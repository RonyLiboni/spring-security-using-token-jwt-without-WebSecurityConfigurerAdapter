package com.microservice.springsecurityjwtdemo.entities.user.dto;

import lombok.Data;

@Data
public class UserModelDto {
	
	private String username;
	
	public UserModelDto(String username) {
		this.username = username;
	}
}
