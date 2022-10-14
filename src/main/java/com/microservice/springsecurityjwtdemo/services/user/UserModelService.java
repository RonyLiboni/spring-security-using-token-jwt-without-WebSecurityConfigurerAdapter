package com.microservice.springsecurityjwtdemo.services.user;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.microservice.springsecurityjwtdemo.entities.user.UserModel;
import com.microservice.springsecurityjwtdemo.entities.user.dto.PasswordFormDto;
import com.microservice.springsecurityjwtdemo.entities.user.dto.PasswordRecoveryFormDto;
import com.microservice.springsecurityjwtdemo.entities.user.dto.UserFormDto;
import com.microservice.springsecurityjwtdemo.entities.user.dto.UserModelDto;
import com.microservice.springsecurityjwtdemo.entities.user.dto.UsernameFormDto;
import com.microservice.springsecurityjwtdemo.events.PasswordRecoveryTokenEvent;
import com.microservice.springsecurityjwtdemo.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserModelService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final ApplicationEventPublisher eventPublisher;
	
	@Transactional
	public UserModelDto registerUser(UserFormDto form) {
		return new UserModelDto(saveEntity(new UserModel(form, passwordEncoder)));
	}
	
	
	private UserModel saveEntity(UserModel user) {
		return userRepository.save(user);
	}
	
	@Transactional
	public void deleteUser() {
		userRepository.delete(getUserByUsername());	
	}
	
	private UserModel getUserByUsername() {
		return findByUsername(getUsername());
	}
	
	private UserModel findByUsername(String username) {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("This username doesn't exist"));
	}

	private String getUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	@Transactional
	public UserModelDto updateOnlyUsername(UsernameFormDto form) {
		return new UserModelDto(updateUsername(form));
	}
	
	@Transactional
	private UserModel updateUsername(UsernameFormDto form) {
		UserModel userByUsername = getUserByUsername();
		userByUsername.setUsername(form.getUsername());
		return userByUsername;
	}
	
	@Transactional
	public void updateOnlyPassword(PasswordFormDto form) {
		UserModel userByUsername = getUserByUsername();
		userByUsername.setPassword(passwordEncoder.encode(form.getPassword()));
	}
	
	@Transactional
	public void sendEmailWithTokenToCreateANewPassword(String username) {
		UserModel user = findByUsername(username);
		user.setPasswordRecoveryToken(createPasswordRecoveryToken());
		eventPublisher.publishEvent(new PasswordRecoveryTokenEvent(user));	
	}

	private String createPasswordRecoveryToken() {
		return UUID.randomUUID().toString() + (System.currentTimeMillis() + 1800000);
	}
	
	@Transactional
	public void validateTokenAndChangePassword(PasswordRecoveryFormDto form) {
		UserModel user = userRepository.findByPasswordRecoveryToken(form.getRecoveryToken())
				.orElseThrow(()-> new RuntimeException("The token you provided doesn't exist."));
		user.setPassword(passwordEncoder.encode(form.getNewPassword()));
		user.setPasswordRecoveryToken(null);
	}
}
