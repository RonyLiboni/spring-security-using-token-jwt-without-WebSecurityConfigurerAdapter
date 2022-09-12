package com.microservice.springsecurityjwtdemo.controllers.user;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.springsecurityjwtdemo.entities.user.dto.UserFormDto;
import com.microservice.springsecurityjwtdemo.entities.user.dto.UserModelDto;
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
	@Operation(summary = "Creates an Customer.")
	@ApiResponse(responseCode= "201", description = "The resource was created with success!")
	public ResponseEntity<UserModelDto> registerUser(@RequestBody @Valid UserFormDto form) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userModelService.registerUser(form));
	}
	
	@DeleteMapping	
	@Operation(summary = "User can self-delete its data if authenticated.", security = { @SecurityRequirement(name = "bearer-key") })
	@ApiResponses(value = {
			@ApiResponse(responseCode= "204", description = "The resource was deleted with success!"),
		    @ApiResponse(responseCode= "403", description = "You don't have enough permissions to access this endpoint"),
		})
	public ResponseEntity<Object> deleteAuthenticatedUser(){
		userModelService.deleteUser();
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
}
