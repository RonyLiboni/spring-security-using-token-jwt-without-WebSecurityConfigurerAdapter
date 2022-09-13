package com.microservice.springsecurityjwtdemo.controllers.authentication;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.microservice.springsecurityjwtdemo.configurations.controllers.ControllerTestTemplate;
import com.microservice.springsecurityjwtdemo.entities.login_form.LoginFormDto;

@Profile("default")
class AuthenticationControllerTest extends ControllerTestTemplate {

	@Test
	void authenticateUser_ShouldReturnStatusCodeCreated_WhenUsernameAndPasswordAreCorrect() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.post(authenticationUri)
				.content(objectMapper.writeValueAsString(LoginFormDto.builder().username("ronald.liboni@acad.pucrs.br").password("123456").build()))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.isCreated());
	}
	
	@Test
	void authenticateUser_ShouldReturnStatusCodeForbidden_WhenUsernameAndPasswordAreNotCorrect() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.post(authenticationUri)
				.content(objectMapper.writeValueAsString(LoginFormDto.builder().username("ronald.liboni@acad.pucrs.br").password("wrongPassword").build()))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers
				.status()
				.isBadRequest());
	}


}