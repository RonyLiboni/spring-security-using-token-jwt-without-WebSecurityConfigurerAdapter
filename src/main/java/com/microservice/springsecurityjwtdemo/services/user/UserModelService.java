package com.microservice.springsecurityjwtdemo.services.user;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.microservice.springsecurityjwtdemo.entities.user.RoleModel;
import com.microservice.springsecurityjwtdemo.entities.user.RoleName;
import com.microservice.springsecurityjwtdemo.entities.user.UserModel;
import com.microservice.springsecurityjwtdemo.entities.user.dto.PasswordFormDto;
import com.microservice.springsecurityjwtdemo.entities.user.dto.UserFormDto;
import com.microservice.springsecurityjwtdemo.entities.user.dto.UserModelDto;
import com.microservice.springsecurityjwtdemo.entities.user.dto.UsernameFormDto;
import com.microservice.springsecurityjwtdemo.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserModelService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserModelDto registerUser(UserFormDto form) {
		return new UserModelDto(saveEntity(UserModel.builder()
				.username(form.getUsername())
				.password(passwordEncoder.encode(form.getPassword()))
				.roles(List.of(
						RoleModel.builder()
						.roleId(2L)
						.roleName(RoleName.ROLE_USER)
						.build()))
				.build()));
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

	

	

}
