package com.microservice.springsecurityjwtdemo.services.emails;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class EmailSenderServiceTest {

	@InjectMocks
	private EmailSenderService emailSenderService;
	@Mock
	private JavaMailSender javaMailSenderMock;
	@Captor
	private ArgumentCaptor<SimpleMailMessage> captor;
	private final String destinatario = "teste@teste.com";
	
	@Test
	void sendEmail_ShouldReturnAnEmailNotNull() {
		emailSenderService.sendEmail(destinatario, "text","subject");
		BDDMockito.verify(javaMailSenderMock).send(captor.capture());
		SimpleMailMessage mensagem = captor.getValue();
		assertThat(mensagem).isNotNull();
		assertThat(mensagem.getTo()[0]).isEqualTo(destinatario);
	}

}
