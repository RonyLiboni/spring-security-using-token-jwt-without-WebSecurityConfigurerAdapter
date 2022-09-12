package com.microservice.springsecurityjwtdemo.validations.username;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.microservice.springsecurityjwtdemo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UsernameMustBeUnique, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return !userRepository.existsByUsername(username);
    }

}
