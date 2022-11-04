package com.microservice.springsecurityjwtdemo.controllers.user;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.springsecurityjwtdemo.annotations.PostMappingDocumentation;
import com.microservice.springsecurityjwtdemo.annotations.PutMappingDocumentation;
import com.microservice.springsecurityjwtdemo.entities.user.dto.PasswordRecoveryFormDto;
import com.microservice.springsecurityjwtdemo.entities.user.dto.UserFormDto;
import com.microservice.springsecurityjwtdemo.entities.user.dto.UserModelDto;
import com.microservice.springsecurityjwtdemo.services.user.UserModelService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/all/v1/users")
@RequiredArgsConstructor
public class UserOpenEndpointsController {
	
	private final UserModelService userModelService;

	@PostMapping
	@PostMappingDocumentation(summary= "Users can self-register in this endpoint.")
	@Tag(name = "Create new User Endpoint", description = "Here, users will be able to create a new user")
	public ResponseEntity<UserModelDto> registerUser(@RequestBody @Valid UserFormDto form) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userModelService.registerUser(form));
	}
	
	@PostMapping("/forgotMyPassword/{username}")
	@PostMappingDocumentation(summary =  "Users will receive in their email a token to change their password.")
	@Tag(name= "Forgot my password Endpoint", description="Here, users will be able to change their password if they forgot it.")
	public ResponseEntity<String> getTokenForForgottenPassword(@PathVariable String username) {
		userModelService.sendEmailWithTokenToCreateANewPassword(username);
		return ResponseEntity.status(HttpStatus.CREATED).body("The instructions to change your password were sent to your e-mail.");
	}
	
	@PutMapping("/forgotMyPassword")
	@PutMappingDocumentation(summary = "Users will use the token they received by email to change their password.")
	@Tag(name= "Forgot my password Endpoint", description="Here, users will be able to change their password if they forgot it.")
	public ResponseEntity<String> changeUserForgottenPasswordThroughToken(@RequestBody @Valid PasswordRecoveryFormDto form) {
		userModelService.validateTokenAndChangePassword(form);
		return ResponseEntity.status(HttpStatus.OK).body("Your password was successfuly changed!");
	}
		
}
