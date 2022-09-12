package com.microservice.springsecurityjwtdemo.services.jwt_token;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.microservice.springsecurityjwtdemo.entities.login_form.LoginFormDto;
import com.microservice.springsecurityjwtdemo.entities.token.JwtTokenDto;
import com.microservice.springsecurityjwtdemo.entities.user.UserModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenService {

	@Value("${jwt.expiration}")
	private String expiration;
	
	@Value("${jwt.secret}")
	private String secret;
		
	public boolean isTokenValid(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public UUID getUserId(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return UUID.fromString(claims.getSubject());
	}
	
	public JwtTokenDto createToken(LoginFormDto form, AuthenticationManager authManager) {
		return new JwtTokenDto(buildToken(createAuthentication(form, authManager)), "Bearer");
	}

	private Authentication createAuthentication(LoginFormDto form, AuthenticationManager authManager) {
		return authManager.authenticate(new UsernamePasswordAuthenticationToken(form.getUsername(), form.getPassword()));
	}
	
	private String buildToken(Authentication authentication) {
		UserModel user = (UserModel) authentication.getPrincipal();
	
		return Jwts.builder()
				.setIssuer("Spring Security Demo with JWT API")
				.setSubject(user.getUserId().toString())
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + Long.parseLong(expiration)))
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}
}