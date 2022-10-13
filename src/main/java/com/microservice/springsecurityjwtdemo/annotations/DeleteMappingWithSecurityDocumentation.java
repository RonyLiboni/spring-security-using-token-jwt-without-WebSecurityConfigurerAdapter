package com.microservice.springsecurityjwtdemo.annotations;

import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Target({METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(security = { @SecurityRequirement(name = "bearer-key") })
@ApiResponses(value = {
		@ApiResponse(responseCode= "204", description = "The resource was deleted with success!"),
	    @ApiResponse(responseCode= "403", description = "You don't have enough permissions to access this endpoint"),
	})
public @interface DeleteMappingWithSecurityDocumentation {
	String summary();
}
