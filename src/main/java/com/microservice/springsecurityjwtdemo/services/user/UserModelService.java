package com.microservice.springsecurityjwtdemo.services.user;

import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.microservice.springsecurityjwtdemo.entities.user.UserModel;
import com.microservice.springsecurityjwtdemo.entities.user.dto.PasswordFormDto;
import com.microservice.springsecurityjwtdemo.entities.user.dto.PasswordRecoveryFormDto;
import com.microservice.springsecurityjwtdemo.entities.user.dto.UserFormDto;
import com.microservice.springsecurityjwtdemo.entities.user.dto.UserModelDto;
import com.microservice.springsecurityjwtdemo.entities.user.dto.UsernameFormDto;
import com.microservice.springsecurityjwtdemo.repositories.UserRepository;
import com.microservice.springsecurityjwtdemo.services.emails.EmailSenderService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserModelService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final EmailSenderService emailSenderService;
	
	public UserModelDto registerUser(UserFormDto form) {
		return new UserModelDto(saveEntity(new UserModel(form)));
	}
	
	@Transactional
	private UserModel saveEntity(UserModel user) {
		return userRepository.save(user);
	}
	
	@Transactional
	public void deleteUser() {
		userRepository.delete(getUserByUsername());	
	}
	
	private UserModel getUserByUsername() {
		return userRepository.findByUsername(getUsername())
				.orElseThrow(() -> new RuntimeException("This username doesn't exist"));
	}

	private String getUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	public UserModelDto updateOnlyUsername(UsernameFormDto form) {
		return new UserModelDto(updateUsername(form));
	}

	private UserModel updateUsername(UsernameFormDto form) {
		UserModel userByUsername = getUserByUsername();
		userByUsername.setUsername(form.getUsername());
		return saveEntity(userByUsername);
	}
	
	public void updateOnlyPassword(PasswordFormDto form) {
		UserModel userByUsername = getUserByUsername();
		userByUsername.setPassword(passwordEncoder.encode(form.getPassword()));
		saveEntity(userByUsername);
	}

	public void sendEmailWithTokenToCreateANewPassword(String username) {
		UserModel user = userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("This username doesn't exist"));
		
		user.setPasswordRecoveryToken(createPasswordRecoveryToken());
		saveEntity(user);
		emailSenderService.sendEmail(username, 
				"Hi, you have asked to change your password. Please send a PUT request to the path bellow. It will expire in 30 minutes: \n"
				+ "http://localhost:8080/v1/user/forgotMyPassword/"+user.getPasswordRecoveryToken()
				+ "\n also you can copy and paste the token in the password recovery endpoint. Token: "+user.getPasswordRecoveryToken()
				+"\n This e-mail is from the app you are testing. \n"
				+ "https://github.com/RonyLiboni/spring-security-using-token-jwt-without-WebSecurityConfigurerAdapter.git",
				"\n You asked to change your password in Spring Security JWT Token service!");
	}

	private String createPasswordRecoveryToken() {
		return UUID.randomUUID().toString() + (System.currentTimeMillis() + 1800000);
	}

	public void validateTokenAndChangePassword(PasswordRecoveryFormDto form) {
		UserModel user = userRepository.findByPasswordRecoveryToken(form.getRecoveryToken())
				.orElseThrow(()-> new RuntimeException("The token you provided doesn't exist."));
		user.setPassword(passwordEncoder.encode(form.getNewPassword()));
		user.setPasswordRecoveryToken(null);
		saveEntity(user);
	}
}
