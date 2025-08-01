package com.eubrunocoelho.ticketing.annotation.validation;

import com.eubrunocoelho.ticketing.validator.UniqueUsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueUsernameValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {

    String message() default "O valor para \"username\" já está cadastrado.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
