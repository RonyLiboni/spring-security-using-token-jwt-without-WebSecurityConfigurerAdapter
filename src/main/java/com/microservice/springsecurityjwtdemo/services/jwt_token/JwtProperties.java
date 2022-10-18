package com.microservice.springsecurityjwtdemo.services.jwt_token;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties("jwt")
@Getter
@Setter
public class JwtProperties {
	private String secret;
	private Long expiration;
}
