package com.microservice.springsecurityjwtdemo.entities.validation.field_errors;

import org.springframework.validation.FieldError;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FieldErrorsDto {
	
	@Schema(example = "username")
	private String field;
	
	@Schema(example = "The username you input is in use, please try another one!")
	private String error;
	
	public FieldErrorsDto (FieldError fieldError) {
		this.field = fieldError.getField();
		this.error = fieldError.getDefaultMessage();
	}	
}
