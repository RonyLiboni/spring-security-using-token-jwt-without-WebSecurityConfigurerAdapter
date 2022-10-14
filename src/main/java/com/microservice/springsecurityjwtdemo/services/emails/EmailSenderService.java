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
	private final EmailProperties emailProperties;
	
	public void sendEmail(String destinatario, String text, String subject) {
		SimpleMailMessage message= new SimpleMailMessage();
		message.setTo(destinatario);
		message.setText(text);
		message.setSubject(subject);
		
		emailSender.send(message);
	}
	
	@EventListener
	public void newRecoveryTokenCreatedListener(PasswordRecoveryTokenEvent event) {
		UserModel user = event.getUser();
		sendEmail(user.getUsername(),
				String.format(emailProperties.getBody(), user.getPasswordRecoveryToken()),
				emailProperties.getSubject());
	}
}
