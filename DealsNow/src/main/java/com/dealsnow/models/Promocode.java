package com.dealsnow.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Promocode {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer promocodeId;
	@NotNull(message = "code cannot be null.")
	@NotBlank(message = "code status cannot be blank.")
	@NotEmpty(message = "code status cannot be empty.")
	private String code;
	
	private Integer amt;
}
