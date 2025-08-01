package com.eubrunocoelho.ticketing.annotation.validation;

import com.eubrunocoelho.ticketing.validator.ExistsTicketIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = ExistsTicketIdValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsTicketId {

    String message() default "Não existe um ticket para este valor de ID em \"ticket\".";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
