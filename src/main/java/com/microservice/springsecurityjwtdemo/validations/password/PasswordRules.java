package com.microservice.springsecurityjwtdemo.validations.password;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = PasswordRulesValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordRules {

	String message() default "Please type a stronger password!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}