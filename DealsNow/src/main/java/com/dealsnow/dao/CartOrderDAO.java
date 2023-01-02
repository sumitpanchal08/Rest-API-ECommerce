package com.dealsnow.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dealsnow.models.CartOrder;
import com.dealsnow.models.User;
@Repository
public interface CartOrderDAO extends JpaRepository<CartOrder, Integer>{
	public List<CartOrder> findByUser(User user);
}
