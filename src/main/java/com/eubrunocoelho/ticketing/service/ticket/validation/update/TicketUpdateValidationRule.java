package com.eubrunocoelho.ticketing.service.ticket.validation.update;

import com.eubrunocoelho.ticketing.entity.Ticket;

public interface TicketUpdateValidationRule
{
    void validate( Ticket ticket );
}
