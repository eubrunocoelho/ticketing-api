package com.eubrunocoelho.ticketing.validation.annotation;

import com.eubrunocoelho.ticketing.validation.validator.UniqueUsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint( validatedBy = UniqueUsernameValidator.class )
@Target( {ElementType.FIELD} )
@Retention( RetentionPolicy.RUNTIME )
public @interface UniqueUsername
{
    String message() default "O valor para \"username\" já está cadastrado.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
