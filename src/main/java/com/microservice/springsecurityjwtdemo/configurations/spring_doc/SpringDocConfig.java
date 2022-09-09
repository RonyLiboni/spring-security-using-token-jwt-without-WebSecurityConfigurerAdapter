package com.microservice.springsecurityjwtdemo.configurations.spring_doc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SpringDocConfig {
	
	@Bean
	public OpenAPI customOpenAPI() {
	 return new OpenAPI()			 
	        .components(new Components()
	        		.addSecuritySchemes("bearer-key",
	        				new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("Bearer").bearerFormat("JWT"))
	        		)
	        .info(new Info().title("Ger Library API")
	        		.version("1.0")
	        		.description("This is a sample server of Ger Library API."));
	}

}
