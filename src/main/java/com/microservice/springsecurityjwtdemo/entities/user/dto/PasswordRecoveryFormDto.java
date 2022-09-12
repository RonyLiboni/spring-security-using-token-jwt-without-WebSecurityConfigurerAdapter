package com.microservice.springsecurityjwtdemo.entities.user.dto;

import javax.validation.constraints.NotBlank;

import com.microservice.springsecurityjwtdemo.validations.password.PasswordRules;
import com.microservice.springsecurityjwtdemo.validations.recovery_token.PasswordRecoveryTokenMustBeValid;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PasswordRecoveryFormDto {

	@PasswordRecoveryTokenMustBeValid
	@Schema(example = "e9cbb841...")
	private String recoveryToken;
	
	@NotBlank
	@PasswordRules
	@Schema(example = "E4sy_pass")
	private String newPassword;
}
