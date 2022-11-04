package com.microservice.springsecurityjwtdemo.controllers.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.microservice.springsecurityjwtdemo.annotations.DeleteMappingWithSecurityDocumentation;
import com.microservice.springsecurityjwtdemo.services.user.UserModelService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/v1/users")
@RequiredArgsConstructor
public class UserAdminEndpointsController {
	
	private final UserModelService userModelService;
	
	@DeleteMapping("/{username}")
	@DeleteMappingWithSecurityDocumentation(summary="Admins can delete a user.")
	@Tag(name = "Authenticated User Endpoints", description = "Here, admins will be able to delete a user's data.")
	public ResponseEntity<Object> deleteAuthenticatedUser(@PathVariable("username") String username){
		userModelService.deleteUserByUsername(username);
		return ResponseEntity.noContent().build();
	}		
}
