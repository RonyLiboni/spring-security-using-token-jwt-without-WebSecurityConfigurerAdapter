package com.microservice.springsecurityjwtdemo.services.emails;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailSenderService {
	
	private final JavaMailSender emailSender;
	private String remetente="testesdeapp.naoresponda@gmail.com";
	
	@Async
	public void sendEmail(String destinatario, String text, String subject) {
		SimpleMailMessage message= new SimpleMailMessage();
		message.setFrom(remetente);
		message.setTo(destinatario);
		message.setText(text);
		message.setSubject(subject);
		
		emailSender.send(message);
	}
}
