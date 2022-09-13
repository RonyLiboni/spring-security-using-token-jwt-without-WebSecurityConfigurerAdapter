package com.microservice.springsecurityjwtdemo.utils;

import com.microservice.springsecurityjwtdemo.entities.login_form.LoginFormDto;
import com.microservice.springsecurityjwtdemo.entities.user.dto.PasswordFormDto;
import com.microservice.springsecurityjwtdemo.entities.user.dto.PasswordRecoveryFormDto;
import com.microservice.springsecurityjwtdemo.entities.user.dto.UserFormDto;
import com.microservice.springsecurityjwtdemo.entities.user.dto.UsernameFormDto;

public class UserUtil {

	public static UserFormDto createInvalidUserFormDto() {
		return new UserFormDto("ronald.liboni@acad.pucrs.br"," ");
	}

	public static UsernameFormDto createInvalidUsernameFormDto() {
		return new UsernameFormDto("");
	}

	public static PasswordFormDto createInvalidPassworFormDto() {
		return new PasswordFormDto(" ");
	}

	public static LoginFormDto createInvalidLoginFormDto() {
		return new LoginFormDto("", "") ;
	}

	public static PasswordRecoveryFormDto createInvalidPasswordRecoveryFormDto() {
		return new PasswordRecoveryFormDto("invalidToken"," ");
	}

	public static LoginFormDto createLoginFormDtoWithWrongUsernameAndPassword() {
		return new LoginFormDto("Test", "test") ;
	}
}
