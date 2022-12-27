package com.dealsnow.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dealsnow.models.Rating;
@Repository
public interface RatingDAO extends JpaRepository<Rating, Integer>{

}
