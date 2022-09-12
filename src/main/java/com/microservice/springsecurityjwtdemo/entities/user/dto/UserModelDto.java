package com.microservice.springsecurityjwtdemo.entities.user.dto;

import com.microservice.springsecurityjwtdemo.entities.user.UserModel;

import lombok.Data;

@Data
public class UserModelDto {
	
	private String username;
	
	public UserModelDto(UserModel userModel) {
		this.username = userModel.getUsername();
	}
}
