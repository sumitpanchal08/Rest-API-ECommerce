package com.dealsnow.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer orderId;
	private Double totalamount;
	private LocalDateTime orderDateTime;
	private String orderStatus;
	
	//User details....(Bidirectional mapping)
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	//Products added to cart for order with quantity....(Unidirectional mapping)
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "orderId")
	private Set<ProductOrderDetails> productOrderDetails;
	
	//Promocode Details...(Unidirectional mapping)
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "promocodeId")
	private Promocode promocode;
	
	//Payment Details....(Unidirectional mapping)
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "paymentId")
	private Payment payment;
	
	//Address details....(Unidirectional mapping)
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "addressId")
	private Address address;
	
	
	
	
	
	
	
	
}
