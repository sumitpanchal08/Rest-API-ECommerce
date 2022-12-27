package com.dealsnow.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer addressId;
	@NotNull(message = "name cannot be null.")
	@NotBlank(message = "name status cannot be blank.")
	@NotEmpty(message = "name status cannot be empty.")
	@Size(min=3,max=24, message="name can be 3 to 24 characters.")
	private String name;
	@NotNull(message = "Mobile cannot be null.")
	@NotBlank(message = "Mobile status cannot be blank.")
	@NotEmpty(message = "Mobile status cannot be empty.")
	@Size(min=10,max=10,message="Mobile should be 10 characters.")
	@Pattern(regexp="(^$|[0-9]{10})",message="Mobile should contains only numbers")
	private String mobile;
	@NotNull(message = "housenumber cannot be null.")
	@NotBlank(message = "housenumber status cannot be blank.")
	@NotEmpty(message = "housenumber status cannot be empty.")
	@Size(min=3,max=24, message="housenumber can be 3 to 24 characters.")
	private String houseNumber;
	@NotNull(message = "city cannot be null.")
	@NotBlank(message = "city status cannot be blank.")
	@NotEmpty(message = "city status cannot be empty.")
	@Size(min=3,max=24, message="city can be 3 to 24 characters.")
	private String city;
	@NotNull(message = "state cannot be null.")
	@NotBlank(message = "state status cannot be blank.")
	@NotEmpty(message = "state status cannot be empty.")
	@Size(min=3,max=24, message="state can be 3 to 24 characters.")
	private String state;
	@NotNull(message = "landmark cannot be null.")
	@NotBlank(message = "landmark status cannot be blank.")
	@NotEmpty(message = "landmark status cannot be empty.")
	@Size(min=3,max=24, message="landmark can be 3 to 24 characters.")
	private String landmark;
	@NotNull(message = "postalCode cannot be null.")
	@NotBlank(message = "postalCode status cannot be blank.")
	@NotEmpty(message = "postalCode status cannot be empty.")
	@Size(min=6,max=6, message="postalCode can be 6 characters.")
	@Pattern(regexp="(^$|[0-9]{6})",message="PostalCode should contains only numbers")
	private String postalCode;
}
