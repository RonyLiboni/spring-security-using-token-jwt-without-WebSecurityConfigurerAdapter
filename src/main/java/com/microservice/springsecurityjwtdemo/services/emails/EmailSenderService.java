package com.microservice.springsecurityjwtdemo.services.emails;

import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.microservice.springsecurityjwtdemo.entities.user.UserModel;
import com.microservice.springsecurityjwtdemo.events.PasswordRecoveryTokenEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailSenderService {
	
	private final JavaMailSender emailSender;
	private String remetente="testesdeapp.naoresponda@gmail.com";
	
	public void sendEmail(String destinatario, String text, String subject) {
		SimpleMailMessage message= new SimpleMailMessage();
		message.setFrom(remetente);
		message.setTo(destinatario);
		message.setText(text);
		message.setSubject(subject);
		
		emailSender.send(message);
	}
	
	@EventListener
	public void newRecoveryTokenCreatedListener(PasswordRecoveryTokenEvent event) {
		UserModel user = event.getUser();
		sendEmail(user.getUsername(), 
				"Hi, you have asked to change your password. Please send a PUT request to the path bellow. It will expire in 30 minutes: \n"
				+ "http://localhost:8080/v1/user/forgotMyPassword/"+user.getPasswordRecoveryToken()
				+ "\n also you can copy and paste the token in the password recovery endpoint. Token: "+user.getPasswordRecoveryToken()
				+"\n This e-mail is from the app you are testing. \n"
				+ "https://github.com/RonyLiboni/spring-security-using-token-jwt-without-WebSecurityConfigurerAdapter.git",
				"\n You asked to change your password in Spring Security JWT Token service!");
	}
}
