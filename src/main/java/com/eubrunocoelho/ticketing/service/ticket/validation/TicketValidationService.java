package com.eubrunocoelho.ticketing.service.ticket.validation;

import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.service.ticket.validation.update.TicketUpdateValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketValidationService
{
    private final TicketUpdateValidator ticketUpdateValidator;

    public void updateValidate( Ticket ticket )
    {
        ticketUpdateValidator.execute( ticket );
    }
}
