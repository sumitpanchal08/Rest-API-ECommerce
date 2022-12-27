package com.dealsnow.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dealsnow.models.User;
@Repository
public interface UserDAO extends JpaRepository<User, Integer>{
	public User findByMobile(String mobile);
	@Query("select u from User u where u.mobile=?1 and u.password=?2 ")
	public User loginUser(String mobile,String password);
}
