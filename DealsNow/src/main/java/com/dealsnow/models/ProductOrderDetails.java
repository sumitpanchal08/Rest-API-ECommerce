package com.dealsnow.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer ProductOrderDetailId;
	private Integer quantity;
//	private Double amount;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="productId")
	private Product product;
}
