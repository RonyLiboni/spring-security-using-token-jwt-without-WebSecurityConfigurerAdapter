package com.microservice.springsecurityjwtdemo.services.emails;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.microservice.springsecurityjwtdemo.entities.user.dto.UserPasswordRecoveryDto;
import com.microservice.springsecurityjwtdemo.events.PasswordRecoveryTokenEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailSenderService {
	
	private final RabbitTemplate rabbitTemplate;
		
	@EventListener
	public void newRecoveryTokenCreatedListener(PasswordRecoveryTokenEvent event) {
		rabbitTemplate.convertAndSend("communication.ex","", new UserPasswordRecoveryDto(event.getUser()));
	}
}
