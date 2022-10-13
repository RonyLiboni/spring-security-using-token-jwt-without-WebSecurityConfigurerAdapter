package com.microservice.springsecurityjwtdemo.services.user;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.microservice.springsecurityjwtdemo.entities.user.UserModel;
import com.microservice.springsecurityjwtdemo.events.PasswordRecoveryTokenEvent;
import com.microservice.springsecurityjwtdemo.repositories.UserRepository;

@ExtendWith(SpringExtension.class)
class UserModelServiceTest {
	
	@InjectMocks
	private UserModelService userModelService;
	@Mock
	private UserRepository userRepository;
	@Mock
	private PasswordEncoder passwordEncoder;
	@Mock
	private ApplicationEventPublisher eventPublisher;
	
	@Test
	void sendEmailWithTokenToCreateANewPassword_ShouldCreateAToken() {
		Mockito.when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(Optional.of(new UserModel()));
		String username = "ronald.liboni@acad.pucrs.br";
		userModelService.sendEmailWithTokenToCreateANewPassword(username);
		Mockito.verify(userRepository).findByUsername(username);
		Mockito.verify(eventPublisher).publishEvent(ArgumentMatchers.any(PasswordRecoveryTokenEvent.class));
	}

}
