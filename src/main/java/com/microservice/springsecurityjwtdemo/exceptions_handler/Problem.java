package com.microservice.springsecurityjwtdemo.exceptions_handler;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.microservice.springsecurityjwtdemo.entities.validation.field_errors.FieldErrorsDto;

import lombok.Builder;
import lombok.Getter;

@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"status","type","title","detail","timestamp"})
@Getter
@Builder
public class Problem {

	private Integer status;
	@Builder.Default
	private LocalDateTime timestamp = LocalDateTime.now();
	private String type;
	private String title;
	private String detail;
	private List<FieldErrorsDto> objectErrors;
	
	public void setObjectErrors(List<FieldErrorsDto> objectErrors) {
		this.objectErrors = objectErrors;
	}
}
