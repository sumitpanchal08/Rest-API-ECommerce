package com.dealsnow.models;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Rating {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer rateId;
	//from 1 to 5 stars
	private Integer rating;
	private LocalDate created;
	private String discription;
	
	//User who give this rating (Unidirectional mapping)
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="userId")
	private User user;
	
	//Product for which the rating given(Bidirectional mapping)
	@ManyToOne
	@JoinColumn(name = "productId")
	private Product product;
	
	
	
	
	
	
	
}
