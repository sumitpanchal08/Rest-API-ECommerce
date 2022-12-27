package com.dealsnow.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dealsnow.models.Category;

public interface CategoryDAO extends JpaRepository<Category, Integer>{

}
