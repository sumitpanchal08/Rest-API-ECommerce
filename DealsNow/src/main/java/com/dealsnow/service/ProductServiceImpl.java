package com.dealsnow.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dealsnow.dao.CategoryDAO;
import com.dealsnow.dao.ProductDAO;
import com.dealsnow.exceptions.CategoryException;
import com.dealsnow.exceptions.ProductException;
import com.dealsnow.models.Category;
import com.dealsnow.models.Product;
import com.dealsnow.models.ProductImg;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	private ProductDAO productdao;
	
	@Autowired
	private CategoryDAO categorydao;
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
		return pr;
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
	public List<Product> viewProducts() throws ProductException {
		// TODO Auto-generated method stub
		List<Product> products=productdao.findAll();
		if(products.size()==0) {
			throw new ProductException("No product found!!");
		}
		return products;
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

	@Override
	public List<Product> searchByCategory(Integer catId) throws ProductException, CategoryException {
		// TODO Auto-generated method stub
		Optional<Category> optional=categorydao.findById(catId);
		if(optional==null) {
			throw new CategoryException("Category Not Found!!");
		}
		List<Product> products=productdao.findByCategory(optional.get());
		return products;
	}

	@Override
	public List<Product> searchByBrand(String m) throws ProductException {
		// TODO Auto-generated method stub
		String pString="%"+m+"%";
		List<Product> products=productdao.searchByBrand(pString);
		if(products.size()==0) {
			throw new ProductException("No product found!!");
		}
		return products;
	}

	@Override
	public List<Product> searchByPriceLessThan(Double m) throws ProductException {
		// TODO Auto-generated method stub
		List<Product> products=productdao.priceLessThen(m);
		if(products.size()==0) {
			throw new ProductException("No product found!!");
		}
		return products;
	}

}
