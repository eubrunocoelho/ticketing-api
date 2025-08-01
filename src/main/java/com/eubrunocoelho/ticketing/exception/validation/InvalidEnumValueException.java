package com.eubrunocoelho.ticketing.exception.validation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidEnumValueException extends RuntimeException {

    public InvalidEnumValueException(String value, Class<? extends Enum<?>> enumType) {
        super("Valor \"" + value + "\" inválido para o tipo \"enum\":" + enumType.getSimpleName() + ".");
    }
}
