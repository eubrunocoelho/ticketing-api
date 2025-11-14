package com.eubrunocoelho.ticketing.exception.business;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( HttpStatus.FORBIDDEN )
public class ReplyAlreadyHasChildException extends RuntimeException
{
    public ReplyAlreadyHasChildException( String message )
    {
        super( message );
    }
}
