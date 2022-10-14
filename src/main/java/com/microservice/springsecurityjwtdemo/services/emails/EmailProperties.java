package com.microservice.springsecurityjwtdemo.services.emails;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties("email.sender")
@Getter
@Setter
public class EmailProperties {
	private String body = "Hi, you have asked to change your password. \n "
			+ "Please send a PUT request to http://localhost:8080/v1/user/forgotMyPassword/ . \n "
			+ "You have to send the token and the new password in a request body. "
			+ "The token will expire in 30 minutes: \n "
			+ "Token: %s "
			+"\n This e-mail is from the app you are testing. \n"
			+ "https://github.com/RonyLiboni/spring-security-using-token-jwt-without-WebSecurityConfigurerAdapter.git";
	private String subject = "\n You asked to change your password in Spring Security JWT Token service!";
}
