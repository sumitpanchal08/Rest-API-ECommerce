package com.dealsnow.service;

import java.util.List;

import com.dealsnow.exceptions.CategoryException;
import com.dealsnow.exceptions.ProductException;
import com.dealsnow.models.Product;
import com.dealsnow.models.ProductImg;

public interface ProductService {
	public Product addProduct(Product product,Integer categoryId)throws ProductException,CategoryException;
	public Product addProductImg(Integer productId,List<ProductImg> imgs)throws ProductException;
	public List<Product> viewProducts() throws ProductException;
	public List<Product> searchProducts(String m) throws ProductException;
	public List<Product> searchByCategory(Integer catId) throws ProductException,CategoryException;
	public List<Product> searchByBrand(String m) throws ProductException;
	public List<Product> searchByPriceLessThan(Double m) throws ProductException;
}
