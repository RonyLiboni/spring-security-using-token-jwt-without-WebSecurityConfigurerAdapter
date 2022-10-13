package com.microservice.springsecurityjwtdemo.controllers.exceptions;

import java.util.List;
import java.util.stream.Collectors;
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
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ApiResponse(responseCode= "400", description = "There was an error with the form you sent.")
	public ResponseEntity<List<FieldErrorsDto>> formFieldsHasErrors(MethodArgumentNotValidException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(exception.getBindingResult().getFieldErrors().stream()
													 .map(FieldErrorsDto::new)
													 .collect(Collectors.toList()));
	}
	
	@ExceptionHandler(RuntimeException.class)
	@ApiResponse(responseCode= "400")
	public ResponseEntity<String> customException(RuntimeException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}

		
	
}
