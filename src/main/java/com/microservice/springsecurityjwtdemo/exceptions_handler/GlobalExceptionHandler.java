package com.microservice.springsecurityjwtdemo.exceptions_handler;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.microservice.springsecurityjwtdemo.entities.validation.field_errors.FieldErrorsDto;
import com.microservice.springsecurityjwtdemo.exceptions.RecoveryTokenException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<FieldErrorsDto> validationErrors = ex.getBindingResult().getFieldErrors()
																		.stream()
																		.map(FieldErrorsDto::new)
																		.collect(Collectors.toList());
		Problem body = buildBody("validation error", status, ProblemType.OBJECT_WITH_VALIDATION_ERROR, validationErrors);
		return handleExceptionInternal(ex, body, headers, status, request);
	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	@ApiResponse(responseCode= "404")
	public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException exception, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		Problem body = buildBody(exception.getMessage(), status, ProblemType.RESOURCE_NOT_FOUND);
		return handleExceptionInternal(exception, body, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(RecoveryTokenException.class)
	@ApiResponse(responseCode= "400")
	public ResponseEntity<Object> handleRecoveryTokenException(RecoveryTokenException exception, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Problem body = buildBody(exception.getMessage(), status, ProblemType.RESOURCE_NOT_FOUND);
		return handleExceptionInternal(exception, body, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(Exception.class)
	@ApiResponse(responseCode= "500", description = "An unexpected error happened in the server side!")
	public ResponseEntity<Object> handleExceptions(Exception exception, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		String detail = "An unexpected error happened in the server side. Please, try again, if the error remains, "
				+ "then contact the system administrator.";
		Problem body = buildBody(detail, status, ProblemType.INTERNAL_SERVER_ERROR);
		return handleExceptionInternal(exception, body, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	@ApiResponse(responseCode= "400")
	public ResponseEntity<Object> handleBadCredentialsException(Exception exception, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Problem body = buildBody(exception.getMessage(), status, ProblemType.INVALID_CREDENTIALS);
		return handleExceptionInternal(exception, body, new HttpHeaders(), status, request);
	}

	private Problem buildBody(String detail, HttpStatus status, ProblemType problemType) {
		return Problem.builder()
			.status(status.value())
			.type(problemType.getUri())
			.title(problemType.getTitle())
			.detail(detail)
			.build();
	}
	
	private Problem buildBody(String detail, HttpStatus status, ProblemType problemType, List<FieldErrorsDto> validationErrors) {
		Problem body = buildBody(detail, status, problemType);
		body.setObjectErrors(validationErrors);		
		return body;
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		if (body == null) {
			body = Problem.builder()
					.title(status.getReasonPhrase())
					.status(status.value())
					.build();
		} else if (body instanceof String) {
			body = Problem.builder()
					.title(ex.getMessage())
					.status(status.value())
					.build();
		}		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {		
		Problem body = buildBody(ex.getRootCause().getMessage(), status, ProblemType.MESSAGE_NOT_READABLE);
		return handleExceptionInternal(ex, body, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String detail =String.format("The resource '%s', that you tried to access, does not exist!", ex.getRequestURL());
		Problem body = buildBody(detail, status, ProblemType.RESOURCE_NOT_FOUND);
		return handleExceptionInternal(ex, body, headers, status, request);
	}
		
}
