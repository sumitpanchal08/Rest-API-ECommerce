package com.dealsnow.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dealsnow.models.CartOrder;
@Repository
public interface CartOrderDAO extends JpaRepository<CartOrder, Integer>{

}
