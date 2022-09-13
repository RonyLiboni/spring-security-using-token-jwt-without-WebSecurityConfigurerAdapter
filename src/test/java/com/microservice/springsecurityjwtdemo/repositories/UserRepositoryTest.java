package com.microservice.springsecurityjwtdemo.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;
	private final String usernameThatExist ="ronald.liboni@acad.pucrs.br";
	private final String usernameThatDoesNotExist="userThatDoesNotExist";
	private final String tokenThatExist ="testToken";
	private final String tokenThatDoesNotExist="tokenThatDoesNotExist";
	
	@Test
	void findByUsername_ShouldReturnAnOptionalOfAUserWithAUser_whenUsernameExistInDataBase() {
		assertThat(userRepository.findByUsername(usernameThatExist)).isNotEmpty();
	}
	
	@Test
	void findByUsername_ShouldReturnAnEmptyOptionalOfAUser_whenUsernameDoesNotExistInDataBase() {
		assertThat(userRepository.findByUsername(usernameThatDoesNotExist)).isEmpty();
	}
	
	@Test
	void findByPasswordRecoveryToken_ShouldReturnAnOptionalOfAUserWithAUser_whenpasswordRecoveryTokenExistInDataBase() {		
		assertThat(userRepository.findByPasswordRecoveryToken(tokenThatExist)).isNotEmpty();
	}
	
	@Test
	void findByPasswordRecoveryToken_ShouldReturnAnEmptyOptionalOfAUser_whenpasswordRecoveryTokenDoesNotExistInDataBase() {
		assertThat(userRepository.findByPasswordRecoveryToken(tokenThatDoesNotExist)).isEmpty();
	}
	
	@Test
	void existsByUsername_ShouldReturnTrue_WhenUsernameExistInDataBase() {
		assertThat(userRepository.existsByUsername(usernameThatExist)).isTrue();
	}
	
	@Test
	void existsByUsername_ShouldReturnFalse_WhenUsernameDoesNotExistInDataBase() {
		assertThat(userRepository.existsByUsername(usernameThatDoesNotExist)).isFalse();
	}
	
	@Test
	void existsByPasswordRecoveryToken_ShouldReturnTrue_WhenPasswordRecoveryTokenExistInDataBase() {
		assertThat(userRepository.existsByPasswordRecoveryToken(tokenThatExist)).isTrue();
	}
	
	@Test
	void existsByPasswordRecoveryToken_ShouldReturnFalse_WhenPasswordRecoveryTokenDoesNotExistInDataBase() {
		assertThat(userRepository.existsByPasswordRecoveryToken(tokenThatDoesNotExist)).isFalse();
	}


}
