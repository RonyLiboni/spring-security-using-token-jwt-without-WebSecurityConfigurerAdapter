package com.microservice.springsecurityjwtdemo.entities.user.dto;

import com.microservice.springsecurityjwtdemo.entities.user.UserModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModelDto {
	
	@Schema(example = "yourEmail@gmail.com")
	private String username;
	
	public UserModelDto(UserModel userModel) {
		this.username = userModel.getUsername();
	}
}
