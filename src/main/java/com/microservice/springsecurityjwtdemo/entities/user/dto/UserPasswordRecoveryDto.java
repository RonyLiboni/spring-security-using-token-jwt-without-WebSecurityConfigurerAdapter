package com.microservice.springsecurityjwtdemo.entities.user.dto;

import com.microservice.springsecurityjwtdemo.entities.user.UserModel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserPasswordRecoveryDto {
	
	private String username;
	private String passwordRecoveryToken;
	
	public UserPasswordRecoveryDto(UserModel user) {
		this.username = user.getUsername();
		this.passwordRecoveryToken = user.getPasswordRecoveryToken();
	}
}
