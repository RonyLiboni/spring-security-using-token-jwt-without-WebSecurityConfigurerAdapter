package com.microservice.springsecurityjwtdemo.controllers.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.microservice.springsecurityjwtdemo.services.user.UserModelService;

@ExtendWith(SpringExtension.class)
class UserControllerTest {

	@InjectMocks
	private UserController userController;
	@Mock
	private UserModelService userModelService;
		
	@Test
	void registerUser_ShouldReturnStatusCodeCreated_WhenSucessfull() throws Exception {
		assertThat(userController.registerUser(null).getStatusCode())
			.isEqualTo(HttpStatus.CREATED);
	}
	
	@Test
	void deleteAuthenticatedUser_ShouldReturnStatusNoContent_WhenUserIsAuthenticated() throws Exception {
		assertThat(userController.deleteAuthenticatedUser().getStatusCode())
			.isEqualTo(HttpStatus.NO_CONTENT);
	}
	
	@Test
	void updateUserUsername_ShouldReturnStatusOk_WhenUserIsAuthenticated() throws Exception {
		assertThat(userController.updateUserUsername(null).getStatusCode())
			.isEqualTo(HttpStatus.OK);
	}
	
	@Test
	void updateUserPassword_ShouldReturnStatusNoContent_WhenUserIsAuthenticated() throws Exception {
		assertThat(userController.updateUserPassword(null).getStatusCode())
			.isEqualTo(HttpStatus.NO_CONTENT);
	}
	
	@Test
	void changeUserForgottenPasswordThroughToken_ShouldReturnStatusCodeOk__WhenSucessfull() throws Exception {	
		assertThat(userController.changeUserForgottenPasswordThroughToken(null).getStatusCode())
			.isEqualTo(HttpStatus.OK);
	}
	
	@Test
	void getTokenForForgottenPassword_ShouldReturnStatusCodeOk__WhenSucessfull() throws Exception {
		assertThat(userController.getTokenForForgottenPassword(null).getStatusCode())
			.isEqualTo(HttpStatus.OK);
	}
}
