package com.dealsnow.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dealsnow.models.CurrentSession;
@Repository
public interface CurrentSessionDAO extends JpaRepository<CurrentSession, Integer> {
	public CurrentSession findByUuid(String uuid);
}
