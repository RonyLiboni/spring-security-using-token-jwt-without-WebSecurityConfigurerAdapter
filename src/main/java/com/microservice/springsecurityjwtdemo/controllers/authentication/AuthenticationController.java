package com.microservice.springsecurityjwtdemo.controllers.authentication;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.springsecurityjwtdemo.annotations.PostMappingDocumentation;
import com.microservice.springsecurityjwtdemo.entities.login_form.LoginFormDto;
import com.microservice.springsecurityjwtdemo.entities.token.JwtTokenDto;
import com.microservice.springsecurityjwtdemo.services.jwt_token.JwtTokenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Endpoint", description = "Here, you will be able authenticate and get a token with your permissions.")
public class AuthenticationController {
	private final JwtTokenService tokenService;
	private final AuthenticationManager authManager;

	@PostMapping
	@PostMappingDocumentation(summary = "Get a token through your username and password.")
	@Operation(summary = "Get a token through your username and password.")
	public ResponseEntity<JwtTokenDto> autenticar(@RequestBody @Valid LoginFormDto form) {
		return ResponseEntity.status(HttpStatus.CREATED).body(tokenService.createToken(form, authManager));
	}		
}
