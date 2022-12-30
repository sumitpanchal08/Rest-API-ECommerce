package com.dealsnow.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dealsnow.dao.CategoryDAO;
import com.dealsnow.exceptions.CategoryException;
import com.dealsnow.models.Category;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryDAO categorydao;
	
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
	public List<Category> viewCategories() throws CategoryException {
		// TODO Auto-generated method stub
		List<Category> categories=categorydao.findAll();
		if(categories.size()==0) {
			throw new CategoryException("No Category Available!!!");
		}
		return categories;
	}

	@Override
	public Category deleteCategory(Integer categoryId) throws CategoryException {
		// TODO Auto-generated method stub
		Optional<Category> optional=categorydao.findById(categoryId);
		if(optional==null) {
			throw new CategoryException("Invalid Category ID");
		}
		categorydao.delete(optional.get());
		return optional.get();
	}
}
