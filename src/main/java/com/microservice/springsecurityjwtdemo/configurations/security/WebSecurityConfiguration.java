package com.microservice.springsecurityjwtdemo.configurations.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.microservice.springsecurityjwtdemo.filters.authentication.JwtTokenAuthenticationFilter;
import com.microservice.springsecurityjwtdemo.repositories.UserRepository;
import com.microservice.springsecurityjwtdemo.services.jwt_token.JwtTokenService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@Profile("default")
public class WebSecurityConfiguration {
	
	private final UserRepository userRepository;
	private final JwtTokenService jwtTokenService;
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	protected AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	} 
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
            .authorizeRequests(auth -> {
            	auth
            		.antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
            		.antMatchers("/v1/user/forgotMyPassword/**").permitAll()
            		.antMatchers(HttpMethod.POST, "/v1/user", "/auth").permitAll()
            		.anyRequest().authenticated();
            })
            .cors().and()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    		.and().addFilterBefore(new JwtTokenAuthenticationFilter(jwtTokenService, userRepository), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowedHeaders("*").exposedHeaders("*").allowedMethods("*");
			}
		};
	}
	
	
}
