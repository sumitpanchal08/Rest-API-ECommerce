package com.dealsnow.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dealsnow.models.ProductOrderDetails;

@Repository
public interface ProductOrderDetailsDAO extends JpaRepository<ProductOrderDetails, Integer>{
//	@Query("select p from ProductOrderDetails p where productId = :m AND orderId = :n ")
//	public ProductOrderDetails checkProductPresent(@Param("m") Integer pid,@Param("n") Integer oid);
}
