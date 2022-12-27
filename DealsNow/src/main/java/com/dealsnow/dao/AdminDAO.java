package com.dealsnow.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dealsnow.models.Admin;
@Repository
public interface AdminDAO extends JpaRepository<Admin, Integer> {
	public Admin findByUsername(String username);
	@Query("select a from Admin a where a.username=?1 and a.password=?2")
	public Admin loginAdmin(String username,String password);
}
