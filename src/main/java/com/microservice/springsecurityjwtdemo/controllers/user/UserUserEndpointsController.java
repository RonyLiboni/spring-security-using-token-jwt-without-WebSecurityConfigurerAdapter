package com.microservice.springsecurityjwtdemo.controllers.user;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.springsecurityjwtdemo.annotations.DeleteMappingWithSecurityDocumentation;
import com.microservice.springsecurityjwtdemo.annotations.PutMappingWithSecurityDocumentation;
import com.microservice.springsecurityjwtdemo.entities.user.dto.PasswordFormDto;
import com.microservice.springsecurityjwtdemo.entities.user.dto.UserModelDto;
import com.microservice.springsecurityjwtdemo.entities.user.dto.UsernameFormDto;
import com.microservice.springsecurityjwtdemo.services.user.UserModelService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user/v1/users")
@RequiredArgsConstructor
public class UserUserEndpointsController {
	
	private final UserModelService userModelService;
	
	@DeleteMapping	
	@DeleteMappingWithSecurityDocumentation(summary="Users can delete their data if authenticated.")
	@Tag(name = "Authenticated User Endpoints", description = "Here, users will be able to update or delete their data.")
	public ResponseEntity<Object> deleteAuthenticatedUser(){
		userModelService.deleteUser();
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@PutMapping("/username")
	@PutMappingWithSecurityDocumentation(summary = "Users can update their username if authenticated.")
	@Tag(name = "Authenticated User Endpoints")
	public ResponseEntity<UserModelDto> updateUserUsername(@RequestBody @Valid UsernameFormDto form) {
		return ResponseEntity.status(HttpStatus.OK).body(userModelService.updateOnlyUsername(form));
	}
	
	@PutMapping("/password")
	@PutMappingWithSecurityDocumentation(summary = "Users can update their password if authenticated.")
	@Tag(name = "Authenticated User Endpoints")
	public ResponseEntity<Object> updateUserPassword(@RequestBody @Valid PasswordFormDto form) {
		userModelService.updateOnlyPassword(form);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
		
}
