package com.dealsnow.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dealsnow.models.Promocode;
@Repository
public interface PromocodeDAO extends JpaRepository<Promocode, Integer>{
	public Optional<Promocode> findByCode(String code);
}
