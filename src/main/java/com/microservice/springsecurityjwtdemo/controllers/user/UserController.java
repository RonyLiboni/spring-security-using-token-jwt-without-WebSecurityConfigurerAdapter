package com.microservice.springsecurityjwtdemo.controllers.user;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.springsecurityjwtdemo.entities.user.dto.PasswordFormDto;
import com.microservice.springsecurityjwtdemo.entities.user.dto.PasswordRecoveryFormDto;
import com.microservice.springsecurityjwtdemo.entities.user.dto.UserFormDto;
import com.microservice.springsecurityjwtdemo.entities.user.dto.UserModelDto;
import com.microservice.springsecurityjwtdemo.entities.user.dto.UsernameFormDto;
import com.microservice.springsecurityjwtdemo.services.user.UserModelService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
@Tag(name = "User Endpoints", description = "Here, users will be able to create, update and delete their data.")
public class UserController {
	
	private final UserModelService userModelService;

	@PostMapping
	@Operation(summary = "Users can self-register in this endpoint.")
	@ApiResponse(responseCode= "201", description = "The user was created with success!")
	public ResponseEntity<UserModelDto> registerUser(@RequestBody @Valid UserFormDto form) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userModelService.registerUser(form));
	}
	
	@DeleteMapping	
	@Operation(summary = "Users can delete their data if authenticated.", security = { @SecurityRequirement(name = "bearer-key") })
	@ApiResponses(value = {
			@ApiResponse(responseCode= "204", description = "The user was deleted with success!"),
		    @ApiResponse(responseCode= "403", description = "You don't have enough permissions to access this endpoint"),
		})
	public ResponseEntity<Object> deleteAuthenticatedUser(){
		userModelService.deleteUser();
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@PutMapping("/username")
	@Operation(summary = "Users can update their username if authenticated.", security = { @SecurityRequirement(name = "bearer-key") })
	@ApiResponses(value = {
			@ApiResponse(responseCode= "200", description = "The username was updated with success!"),
		    @ApiResponse(responseCode= "403", description = "You don't have enough permissions to access this endpoint"),
		})
	public ResponseEntity<UserModelDto> updateUserUsername(@RequestBody @Valid UsernameFormDto form) {
		return ResponseEntity.status(HttpStatus.OK).body(userModelService.updateOnlyUsername(form));
	}
	
	@PutMapping("/password")
	@Operation(summary = "Users can update their password if authenticated.", security = { @SecurityRequirement(name = "bearer-key") })
	@ApiResponses(value = {
			@ApiResponse(responseCode= "204", description = "The password was updated with success!"),
		    @ApiResponse(responseCode= "403", description = "You don't have enough permissions to access this endpoint"),
		})
	public ResponseEntity<Object> updateUserPassword(@RequestBody @Valid PasswordFormDto form) {
		userModelService.updateOnlyPassword(form);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@GetMapping("/forgotMyPassword/{username}")
	@Operation(summary = "Users will receive in their email a token to change their password.")
	@ApiResponse(responseCode= "200", description = "The email was sent with success!")
	public ResponseEntity<String> getTokenForForgottenPassword(@PathVariable String username) {
		userModelService.sendEmailWithTokenToCreateANewPassword(username);
		return ResponseEntity.status(HttpStatus.OK).body("The instructions to change your password were sent to your e-mail.");
	}
	
	@PutMapping("/forgotMyPassword")
	@Operation(summary = "Users will use the token they received by email to change their password.")
	@ApiResponse(responseCode= "200", description = "The password was updated with success!")
	public ResponseEntity<String> changeUserForgottenPasswordThroughToken(@RequestBody @Valid PasswordRecoveryFormDto form) {
		userModelService.validateTokenAndChangePassword(form);
		return ResponseEntity.status(HttpStatus.OK).body("Your password was successfuly changed!");
	}
		
}
