package com.microservice.springsecurityjwtdemo.controllers.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.microservice.springsecurityjwtdemo.entities.validation.field_errors.FieldErrorsDto;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandlerController {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ApiResponse(responseCode= "400", description = "There was an error with the form you sent.")
	public ResponseEntity<List<FieldErrorsDto>> formFieldsHasErrors(MethodArgumentNotValidException exception) {
		List<FieldErrorsDto> fieldErrorsdto = new ArrayList<>();
		
		exception.getBindingResult().getFieldErrors().forEach(error ->{
			fieldErrorsdto.add(FieldErrorsDto.builder()
					.field(error.getField())
					.error(error.getDefaultMessage())
					.build());
		});;

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fieldErrorsdto);
	}
	
	@ExceptionHandler(RuntimeException.class)
	@ApiResponse(responseCode= "400", description = "There was an error with the form you sent.")
	public ResponseEntity<String> customException(RuntimeException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}

		
	
}
