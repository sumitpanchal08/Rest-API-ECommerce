package com.dealsnow.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dealsnow.models.Address;
@Repository
public interface AddressDAO extends JpaRepository<Address, Integer>{

}
