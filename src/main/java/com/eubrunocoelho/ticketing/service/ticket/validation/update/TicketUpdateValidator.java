package com.eubrunocoelho.ticketing.service.ticket.validation.update;

import com.eubrunocoelho.ticketing.entity.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TicketUpdateValidator
{
    private final List<TicketUpdateValidationRule> strategies;

    public void execute( Ticket ticket )
    {
        strategies
                .forEach( strategy -> strategy.validate( ticket ) );
    }
}
