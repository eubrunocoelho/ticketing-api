package com.eubrunocoelho.ticketing.exception.business;

public class TicketClosedReplyNotAllowedException extends RuntimeException
{
    public TicketClosedReplyNotAllowedException( String message )
    {
        super( message );
    }
}
