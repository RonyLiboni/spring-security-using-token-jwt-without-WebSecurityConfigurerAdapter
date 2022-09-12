package com.microservice.springsecurityjwtdemo.validations.recovery_token;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.microservice.springsecurityjwtdemo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PasswordTokenRecoveryValidator implements ConstraintValidator<PasswordRecoveryTokenMustBeValid, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String passwordRecoveryToken, ConstraintValidatorContext context) {
        
    	if (passwordRecoveryToken == null 
        		|| passwordRecoveryToken == "" 
        		|| !userRepository.existsByPasswordRecoveryToken(passwordRecoveryToken))
        	return false;

    	if(isPasswordRecoveryTokenExpired(passwordRecoveryToken)) 
    		return false;

    	return true;
    }
    
    private boolean isPasswordRecoveryTokenExpired(String passwordRecoveryToken){   	
    	return Long.parseLong(passwordRecoveryToken.substring(36)) < System.currentTimeMillis();
    }

}
