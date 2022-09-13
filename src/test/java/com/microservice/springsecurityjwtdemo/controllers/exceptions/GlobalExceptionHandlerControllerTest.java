package com.microservice.springsecurityjwtdemo.controllers.exceptions;

import static org.assertj.core.api.Assertions.assertThat;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.microservice.springsecurityjwtdemo.configurations.controllers.ControllerTestTemplate;
import com.microservice.springsecurityjwtdemo.utils.UserUtil;

class GlobalExceptionHandlerControllerTest extends ControllerTestTemplate {
	
	private final String userUri="/v1/user";
	private final String changeUsernameUri="/v1/user/username";
	private final String changePasswordUri="/v1/user/password";
	private final String forgotPasswordUri="/v1/user/forgotMyPassword";
	private final String authUri="/auth";
	
	@Test
	void userFormDtoValidation_userController_shouldReturnBadRequestAndValidationErrors_WhenThereAreInvalidFieldsInTheFormSent() throws Exception {
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders
				.post(userUri)
				.content(objectMapper.writeValueAsString(UserUtil.createInvalidUserFormDto()))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.isBadRequest());

		String errors = result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		assertThat(errors).contains("Password must be at least 8 characters in length");
		assertThat(errors).contains("Password must contain at least 1 uppercase characters");
		assertThat(errors).contains("Password must contain at least 1 lowercase characters");
		assertThat(errors).contains("Password must contain at least 1 digit characters");
		assertThat(errors).contains("Password must contain at least 1 special characters.");
		assertThat(errors).contains("Password cannot contain whitespace characters");
		assertThat(errors).contains("This username is already in use!");
		
	}
	
	@Test
	void usernameFormDtoValidation_userController_shouldReturnBadRequestAndValidationErrors_WhenThereAreInvalidFieldsInTheFormSent() throws Exception  {
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders
				.put(changeUsernameUri)
				.content(objectMapper.writeValueAsString(UserUtil.createInvalidUsernameFormDto()))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.isBadRequest());

		String errors = result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		assertThat(errors).contains("username");
	}

	@Test
	void passwordRecoveryFormDtoValidation_userController_shouldReturnBadRequestAndValidationErrors_WhenThereAreInvalidFieldsInTheFormSent() throws Exception {
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders
				.put(forgotPasswordUri)
				.content(objectMapper.writeValueAsString(UserUtil.createInvalidPasswordRecoveryFormDto()))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.isBadRequest());

		String errors = result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		assertThat(errors).contains("The token you provided is not valid or is expired!");
		assertThat(errors).contains("Password must be at least 8 characters in length");
		assertThat(errors).contains("Password must contain at least 1 uppercase characters");
		assertThat(errors).contains("Password must contain at least 1 lowercase characters");
		assertThat(errors).contains("Password must contain at least 1 digit characters");
		assertThat(errors).contains("Password must contain at least 1 special characters.");
		assertThat(errors).contains("Password cannot contain whitespace characters");
	}
	
	@Test
	void changeUserForgottenPasswordThroughToken_userController_shouldReturnBadRequest_WhenTheUsernameSentDoesNotExist() throws Exception {
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders
				.get(forgotPasswordUri+"/usernameThatDoesNotExist"))
		.andExpect(MockMvcResultMatchers
				.status()
				.isBadRequest());

		String errors = result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		assertThat(errors).contains("This username doesn't exist");
	}
	
	@Test
	void passwordFormDtoValidation_userController_shouldReturnBadRequestAndValidationErrors_WhenThereAreInvalidFieldsInTheFormSent() throws Exception {
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders
				.put(changePasswordUri)
				.content(objectMapper.writeValueAsString(UserUtil.createInvalidPassworFormDto()))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.isBadRequest());

		String errors = result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		assertThat(errors).contains("Password must be at least 8 characters in length");
		assertThat(errors).contains("Password must contain at least 1 uppercase characters");
		assertThat(errors).contains("Password must contain at least 1 lowercase characters");
		assertThat(errors).contains("Password must contain at least 1 digit characters");
		assertThat(errors).contains("Password must contain at least 1 special characters.");
		assertThat(errors).contains("Password cannot contain whitespace characters");
	}
	
	@Test
	void loginFormDtoValidation_userController_shouldReturnBadRequestAndValidationErrors_WhenThereAreInvalidFieldsInTheFormSent() throws Exception {
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders
				.post(authUri)
				.content(objectMapper.writeValueAsString(UserUtil.createInvalidLoginFormDto()))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.isBadRequest());

		String errors = result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		assertThat(errors).contains("username");
		assertThat(errors).contains("password");
	}
	
	@Test
	void loginFormDtoValidation_userController_shouldReturnBadRequestAndMessageBadCredential_WhenUsernameOrPasswordAreNotInTheDatabase() throws Exception {
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders
				.post(authUri)
				.content(objectMapper.writeValueAsString(UserUtil.createLoginFormDtoWithWrongUsernameAndPassword()))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.isBadRequest());

		String errors = result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
		
		assertThat(errors).contains("Bad credentials");
	}

}
