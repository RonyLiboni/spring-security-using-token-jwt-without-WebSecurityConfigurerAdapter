package com.microservice.springsecurityjwtdemo.entities.user.dto;

import com.microservice.springsecurityjwtdemo.entities.user.UserModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserModelDto {
	
	@Schema(example = "yourEmail@gmail.com")
	private String username;
	
	public UserModelDto(UserModel userModel) {
		this.username = userModel.getUsername();
	}
}
