package com.microservice.springsecurityjwtdemo.exceptions;

public class RecoveryTokenException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RecoveryTokenException(String mensagem) {
		super(mensagem);
	}
	
}
