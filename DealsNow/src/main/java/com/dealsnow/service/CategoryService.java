package com.dealsnow.service;

import java.util.List;

import com.dealsnow.exceptions.CategoryException;
import com.dealsnow.models.Category;

public interface CategoryService {
	public Category addCategory(Category category)throws CategoryException;
	public List<Category> viewCategories() throws CategoryException;
	public Category deleteCategory(Integer categoryId) throws CategoryException;
}
