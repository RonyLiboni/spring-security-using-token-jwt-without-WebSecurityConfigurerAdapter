package com.microservice.springsecurityjwtdemo.entities.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.microservice.springsecurityjwtdemo.validations.password.PasswordRules;
import com.microservice.springsecurityjwtdemo.validations.username.UsernameMustBeUnique;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFormDto {
	
	@NotBlank
	@Email
	@UsernameMustBeUnique
	@Schema(example = "yourEmail@gmail.com")
	private String username;
	
	@NotBlank
	@PasswordRules
	@Schema(example = "E4sy_pass")
	private String password;
	
}
