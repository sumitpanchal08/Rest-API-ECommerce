package com.dealsnow.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dealsnow.models.Category;
import com.dealsnow.models.Product;
@Repository
public interface ProductDAO extends JpaRepository<Product, Integer>{
	@Query("select p from Product p where name LIKE :m ")
	public List<Product> search(@Param("m") String g);
	public List<Product> findByCategory(Category category);
}
