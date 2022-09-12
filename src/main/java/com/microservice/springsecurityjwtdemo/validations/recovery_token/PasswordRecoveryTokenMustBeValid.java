package com.microservice.springsecurityjwtdemo.validations.recovery_token;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordTokenRecoveryValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordRecoveryTokenMustBeValid {

    String message() default "The token you provided is not valid or is expired!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
