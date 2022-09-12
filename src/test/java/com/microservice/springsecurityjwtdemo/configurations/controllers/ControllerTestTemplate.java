package com.microservice.springsecurityjwtdemo.configurations.controllers;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public abstract class ControllerTestTemplate {
	
	@Autowired
	protected MockMvc mockMvc;
	@Autowired
	protected ObjectMapper objectMapper;
	@Autowired
	protected Flyway flyway;

	
	@BeforeEach
	void setup(){
		flyway.clean();
		flyway.migrate();
	}
}
