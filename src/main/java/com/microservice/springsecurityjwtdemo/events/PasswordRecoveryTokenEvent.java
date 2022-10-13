package com.microservice.springsecurityjwtdemo.events;

import com.microservice.springsecurityjwtdemo.entities.user.UserModel;

public class PasswordRecoveryTokenEvent {
	
	private UserModel user;
	
	public PasswordRecoveryTokenEvent(UserModel user) {
		this.user = user;
	}
	
	public UserModel getUser() {
		return user;
	}

}
