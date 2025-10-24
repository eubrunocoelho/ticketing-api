package com.eubrunocoelho.ticketing.validation.annotation;

import com.eubrunocoelho.ticketing.validation.validator.ExistsCategoryIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint( validatedBy = ExistsCategoryIdValidator.class )
@Target( {ElementType.FIELD, ElementType.PARAMETER} )
@Retention( RetentionPolicy.RUNTIME )
public @interface ExistsCategoryId
{
    String message() default "Não existe uma categoria para este valor de ID em \"category\".";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
