package com.eubrunocoelho.ticketing.exception.business;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( HttpStatus.FORBIDDEN )
public class TicketClosedReplyNotAllowedException extends RuntimeException
{
    public TicketClosedReplyNotAllowedException( String message )
    {
        super( message );
    }
}
