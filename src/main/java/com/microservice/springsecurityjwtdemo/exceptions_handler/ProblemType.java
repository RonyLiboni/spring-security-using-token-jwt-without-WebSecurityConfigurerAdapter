package com.microservice.springsecurityjwtdemo.exceptions_handler;

import lombok.Getter;

@Getter
public enum ProblemType {

	RESOURCE_NOT_FOUND("/resource-not-found", "Resource not found."), 
	MESSAGE_NOT_READABLE("/message-not-readable","Message not readable."), 
	INTERNAL_SERVER_ERROR("/internal-server-error","Internal Server Error."), 
	OBJECT_WITH_VALIDATION_ERROR("/object-validation-error","Object Validation Error."), 
	INVALID_CREDENTIALS("/invalid-credentials","You inserted invalid username or password.");

	private String title;
	private String uri;
	
	ProblemType(String path, String title) {
		this.uri = "https://spring.security.demo" + path;
		this.title = title;
	}
	
}
