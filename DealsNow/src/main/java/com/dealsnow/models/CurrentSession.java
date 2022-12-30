package com.dealsnow.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class CurrentSession {
	@Id
	private Integer userId;
	private String uuid;
	private LocalDateTime localDateTime;
	//true for admin and false for user
	private Boolean type;
	
	public Boolean getType() {
		return this.type;
	}
}
