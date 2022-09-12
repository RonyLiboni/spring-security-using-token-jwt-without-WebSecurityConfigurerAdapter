package com.microservice.springsecurityjwtdemo.validations.username;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueUsernameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameMustBeUnique {

    String message() default "This username is already in use!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
