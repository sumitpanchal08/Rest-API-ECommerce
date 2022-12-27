package com.dealsnow.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dealsnow.dao.AdminDAO;
import com.dealsnow.dao.CategoryDAO;
import com.dealsnow.dao.CurrentSessionDAO;
import com.dealsnow.dao.ProductDAO;
import com.dealsnow.dao.PromocodeDAO;
import com.dealsnow.exceptions.AdminException;
import com.dealsnow.exceptions.CategoryException;
import com.dealsnow.exceptions.ProductException;
import com.dealsnow.exceptions.PromocodeException;
import com.dealsnow.models.Admin;
import com.dealsnow.models.AdminDTO;
import com.dealsnow.models.Category;
import com.dealsnow.models.CurrentSession;
import com.dealsnow.models.Product;
import com.dealsnow.models.ProductImg;
import com.dealsnow.models.Promocode;

import net.bytebuddy.utility.RandomString;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminDAO admindao;

	@Autowired
	private CurrentSessionDAO csdao;
	
	@Autowired
	private ProductDAO productdao;
	
	@Autowired
	private CategoryDAO categorydao;
	
	@Autowired
	private PromocodeDAO promocodedao;
	
	@Override
	public Admin registerAdmin(Admin admin) throws AdminException {
		Admin adminByUsername=admindao.findByUsername(admin.getUsername());
		if(adminByUsername!=null) {
			throw new AdminException("Username already registered!!");
		}
		Admin admin2=admindao.save(admin);
		if(admin2==null) {
			throw new AdminException("Admin register Failed!!");
		}
		return admin2;
	}

	@Override
	public Admin loginAdmin(AdminDTO adminDTO) throws AdminException {
		Admin admin=admindao.loginAdmin(adminDTO.getUsername(),adminDTO.getPassword());
		if (admin==null) {
			throw new AdminException("Admin username and password not match!!");
		}
		Optional<CurrentSession> currentSession=csdao.findById(admin.getAdminId());
		if(currentSession!=null) {
			throw new AdminException("Admin already login!!");
		}
		String key=RandomString.make(6);
		
		CurrentSession cs=new CurrentSession(admin.getAdminId(),key,LocalDateTime.now());
		csdao.save(cs);
		return admin;
	}
	@Override
	public String logoutAdmin(Integer userId) throws AdminException {
        Optional<CurrentSession> cs=csdao.findById(userId);
		if(cs==null) {
			throw new AdminException("User Not Logged In with this number");
		}
		csdao.delete(cs.get());
		return "Logout Successfull!!";
	}

	@Override
	public Product addProduct(Product product,Integer categoryId) throws ProductException,CategoryException {
		// TODO Auto-generated method stub
	    Optional<Category> optional=categorydao.findById(categoryId);
	    if(optional==null) {
	    	throw new CategoryException("Category not Found");
	    }
	    product.setCategory(optional.get());
	    product.setAddDateTime(LocalDateTime.now());
		Product pr=productdao.save(product);
		if(pr==null) {
			throw new ProductException("Add Product Failed!!!");
		}
		return null;
	}

	@Override
	public Product addProductImg(Integer productId,List<ProductImg> imgs) throws ProductException {
		// TODO Auto-generated method stub
		Optional<Product> optional=productdao.findById(productId);
		if(optional==null) {
			throw new ProductException("Product Not Found!!");
		}
		Product product=optional.get();
	    Set<ProductImg> list=product.getImgs();
	    for(ProductImg img:imgs) {
	    	list.add(img);
	    }
	    product.setImgs(list);
	    productdao.save(product);
		return product;
	}

	@Override
	public Category addCategory(Category category) throws CategoryException {
		// TODO Auto-generated method stub
		Category category2=categorydao.save(category);
		if(category2==null) {
			throw new CategoryException("Category not Added!! Failed...");
		}
		return category2;
	}

	@Override
	public Promocode addPromocode(Promocode promocode) throws PromocodeException {
		// TODO Auto-generated method stub
		Promocode promocode2=promocodedao.save(promocode);
		if(promocode2==null) {
			throw new PromocodeException("Promocode not Added!! Failed...");
		}
		return promocode2;
	}

	@Override
	public List<Product> viewProducts() throws ProductException {
		// TODO Auto-generated method stub
		List<Product> products=productdao.findAll();
		if(products.size()==0) {
			throw new ProductException("No product found!!");
		}
		return products;
	}

	@Override
	public List<Promocode> viewPromocodes() throws PromocodeException {
		// TODO Auto-generated method stub
		List<Promocode> promocodes=promocodedao.findAll();
		if(promocodes.size()==0) {
			throw new ProductException("No Promocode found!!!");
		}
		return promocodes;
	}

	@Override
	public List<Product> searchProducts(String m) throws ProductException {
		// TODO Auto-generated method stub
		String pString="%"+m+"%";
		List<Product> products=productdao.search(pString);
		if(products.size()==0) {
			throw new ProductException("No product found!!");
		}
		return products;
	}
	
	
	
	
}
