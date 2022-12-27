package com.dealsnow.service;

import java.util.List;

import com.dealsnow.exceptions.AdminException;
import com.dealsnow.exceptions.CategoryException;
import com.dealsnow.exceptions.ProductException;
import com.dealsnow.exceptions.PromocodeException;
import com.dealsnow.models.Admin;
import com.dealsnow.models.AdminDTO;
import com.dealsnow.models.Category;
import com.dealsnow.models.Product;
import com.dealsnow.models.ProductImg;
import com.dealsnow.models.Promocode;

public interface AdminService {
	public Admin registerAdmin(Admin admin)throws AdminException;
	public Admin loginAdmin(AdminDTO adminDTO)throws AdminException;
	public String logoutAdmin(Integer userId)throws AdminException;
	
	public Product addProduct(Product product,Integer categoryId)throws ProductException,CategoryException;
	public Product addProductImg(Integer productId,List<ProductImg> imgs)throws ProductException;
	public Category addCategory(Category category)throws CategoryException;
	public Promocode addPromocode(Promocode promocode)throws PromocodeException;
	
	public List<Product> viewProducts() throws ProductException;
	public List<Promocode> viewPromocodes() throws PromocodeException;
	
	public List<Product> searchProducts(String m) throws ProductException;
}
