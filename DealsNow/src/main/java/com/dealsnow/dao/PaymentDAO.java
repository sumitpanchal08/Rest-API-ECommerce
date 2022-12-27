package com.dealsnow.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dealsnow.models.Payment;
@Repository
public interface PaymentDAO extends JpaRepository<Payment, Integer>{

}
