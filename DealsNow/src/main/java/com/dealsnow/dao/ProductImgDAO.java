package com.dealsnow.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dealsnow.models.ProductImg;
@Repository
public interface ProductImgDAO extends JpaRepository<ProductImg, Integer>{

}
