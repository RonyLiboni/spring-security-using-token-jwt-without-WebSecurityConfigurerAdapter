package com.microservice.springsecurityjwtdemo.entities.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
//	@UniqueUsername
	@Schema(example = "yourEmail@gmail.com")
	private String username;
	
	@NotBlank
	@Schema(example = "E4sy_pass")
	private String password;
	
}
