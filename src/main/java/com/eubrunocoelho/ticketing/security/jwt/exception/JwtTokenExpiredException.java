package com.eubrunocoelho.ticketing.security.jwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.AuthenticationException;

@ResponseStatus( HttpStatus.UNAUTHORIZED )
public class JwtTokenExpiredException extends AuthenticationException
{
    public JwtTokenExpiredException( String message )
    {
        super( message );
    }
}
