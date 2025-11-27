package com.eubrunocoelho.ticketing.exception.jwt;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.AuthenticationException;

@ResponseStatus( HttpStatus.UNAUTHORIZED )
public class JwtTokenMalformedException extends AuthenticationException
{
    public JwtTokenMalformedException( String message )
    {
        super( message );
    }
}
