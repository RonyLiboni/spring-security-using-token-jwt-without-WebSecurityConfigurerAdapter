package com.microservice.springsecurityjwtdemo.services.user;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.microservice.springsecurityjwtdemo.entities.user.RoleModel;
import com.microservice.springsecurityjwtdemo.entities.user.RoleName;
import com.microservice.springsecurityjwtdemo.entities.user.UserModel;
import com.microservice.springsecurityjwtdemo.entities.user.dto.UserFormDto;
import com.microservice.springsecurityjwtdemo.entities.user.dto.UserModelDto;
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
				.build())
				.getUsername());
	}
	
	@Transactional
	private UserModel saveEntity(UserModel user) {
		return userRepository.save(user);
	}

}
