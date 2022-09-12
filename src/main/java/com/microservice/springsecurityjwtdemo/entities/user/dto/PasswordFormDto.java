package com.microservice.springsecurityjwtdemo.entities.user.dto;

import javax.validation.constraints.NotBlank;
import com.microservice.springsecurityjwtdemo.validations.password.PasswordRules;
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
public class PasswordFormDto {
		
	@NotBlank
	@PasswordRules
	@Schema(example = "E4sy_pass")
	private String password;
	
}
