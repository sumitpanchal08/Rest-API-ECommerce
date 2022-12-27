package com.dealsnow.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dealsnow.models.ProductOrderDetails;

@Repository
public interface ProductOrderDetailsDAO extends JpaRepository<ProductOrderDetails, Integer>{
	
}
