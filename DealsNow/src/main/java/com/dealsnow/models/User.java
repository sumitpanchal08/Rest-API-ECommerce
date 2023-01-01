package com.dealsnow.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;
	@NotNull(message = "firstname cannot be null.")
	@NotBlank(message = "firstname status cannot be blank.")
	@NotEmpty(message = "firstname status cannot be empty.")
	@Size(min=3,max=24, message="First name can be 3 to 24 characters.")
	private String firstName;
	private String lastName;
	@NotNull(message = "Mobile cannot be null.")
	@NotBlank(message = "Mobile status cannot be blank.")
	@NotEmpty(message = "Mobile status cannot be empty.")
	@Size(min=10,max=10,message="Mobile should be 10 characters.")
	@Pattern(regexp="(^$|[0-9]{10})",message="Mobile should contains only numbers")
	private String mobile;
	@NotNull(message = "Email cannot be null.")
	@NotBlank(message = "Email status cannot be blank.")
	@NotEmpty(message = "Email status cannot be empty.")
	@Email
	private String email;
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{5,15}$",message = "password must contain atleast 1 uppercase, 1 lowercase, 1 special character and 1 digit ")
    private String password;
	@NotNull(message = "DOB cannot be null.")
	@Past(message="dob should be only past")
	private LocalDate dob;
	private LocalDateTime registerDateTime;
	private LocalDateTime lastmodified;
	
	//Addresses linked with User(Unidirectional Mapping)
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="user_id")
	private Set<Address> addresses;
	
	//Orders linked with User(bidirectional Mapping)
	@OneToMany(mappedBy="user",cascade = CascadeType.ALL)
	private List<CartOrder> orders;
}
