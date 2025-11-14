package com.eubrunocoelho.ticketing.exception.business;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( HttpStatus.FORBIDDEN )
public class TicketResolvedReplyNotAllowedException extends RuntimeException
{
    public TicketResolvedReplyNotAllowedException( String message )
    {
        super( message );
    }
}
