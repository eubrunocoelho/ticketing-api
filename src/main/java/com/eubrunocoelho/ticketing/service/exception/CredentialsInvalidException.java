package com.eubrunocoelho.ticketing.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class CredentialsInvalidException extends AuthenticationException {

    public CredentialsInvalidException(String message) {
        super(message);
    }
}
