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
@Tag(name = "User Endpoints", description = "Here, you will be able to create, update and delete users.")
public class UserController {
	
	private final UserModelService userModelService;

	@PostMapping
	@Operation(summary = "User can self-register in the API.")
	@ApiResponse(responseCode= "201", description = "The resource was created with success!")
	public ResponseEntity<UserModelDto> registerUser(@RequestBody @Valid UserFormDto form) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userModelService.registerUser(form));
	}
	
	@DeleteMapping	
	@Operation(summary = "User can delete its data if authenticated.", security = { @SecurityRequirement(name = "bearer-key") })
	@ApiResponses(value = {
			@ApiResponse(responseCode= "204", description = "The resource was deleted with success!"),
		    @ApiResponse(responseCode= "403", description = "You don't have enough permissions to access this endpoint"),
		})
	public ResponseEntity<Object> deleteAuthenticatedUser(){
		userModelService.deleteUser();
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@PutMapping("/username")
	@Operation(summary = "User can update its username if authenticated.", security = { @SecurityRequirement(name = "bearer-key") })
	@ApiResponses(value = {
			@ApiResponse(responseCode= "200", description = "The resources was updated with success!"),
		    @ApiResponse(responseCode= "403", description = "You don't have enough permissions to access this endpoint"),
		})
	public ResponseEntity<UserModelDto> updateUserUsername(@RequestBody @Valid UsernameFormDto form) {
		return ResponseEntity.status(HttpStatus.OK).body(userModelService.updateOnlyUsername(form));
	}
	
	@PutMapping("/password")
	@Operation(summary = "User can update its password if authenticated.", security = { @SecurityRequirement(name = "bearer-key") })
	@ApiResponses(value = {
			@ApiResponse(responseCode= "204", description = "The resources was updated with success!"),
		    @ApiResponse(responseCode= "403", description = "You don't have enough permissions to access this endpoint"),
		})
	public ResponseEntity<Object> updateUserPassword(@RequestBody @Valid PasswordFormDto form) {
		userModelService.updateOnlyPassword(form);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@GetMapping("/forgotMyPassword/{username}")
	@Operation(summary = "User will receive a token to change its password.")
	@ApiResponse(responseCode= "200", description = "The resources was updated with success!")
	public ResponseEntity<String> getTokenForForgottenPassword(@PathVariable String username) {
		userModelService.sendEmailWithTokenToCreateANewPassword(username);
		return ResponseEntity.status(HttpStatus.OK).body("The instructions to change your password where sent to your e-mail.");
	}
	
	@PutMapping("/forgotMyPassword")
	@Operation(summary = "User will receive a token to change its password.")
	@ApiResponse(responseCode= "200", description = "The resources was updated with success!")
	public ResponseEntity<String> changeUserForgottenPasswordThroughToken(@RequestBody @Valid PasswordRecoveryFormDto form) {
		userModelService.validateTokenAndChangePassword(form);
		return ResponseEntity.status(HttpStatus.OK).body("Your password was successfuly changed!");
	}
	
	
	
	
}
