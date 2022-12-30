package com.dealsnow.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer productId;
	private String name;
	private String brand;
	private Double marketPrice;
	private Double sellPrice;
	private LocalDateTime addDateTime;
	
//	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "categoryId")
	private Category category;
	
	//Product Images...(Unidirectional mapping)
//	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="productId")
	private Set<ProductImg> imgs;
	
	//Product ratings (Bidirectional mapping)
	@JsonIgnore
	@OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
	private List<Rating> ratings;
}
