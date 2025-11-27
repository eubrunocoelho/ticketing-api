package com.eubrunocoelho.ticketing.service.ticket.validation.update.rule;

import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.exception.business.TicketUpdateNotAllowedForStatusException;
import com.eubrunocoelho.ticketing.repository.TicketRepository;
import com.eubrunocoelho.ticketing.service.ticket.validation.update.TicketUpdateValidationRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class TicketUpdatableStatusRule implements TicketUpdateValidationRule
{
    private final TicketRepository ticketRepository;

    private static final Set<Ticket.Status> UNEDITABLE_STATUSES = Set
            .of( Ticket.Status.IN_PROGRESS, Ticket.Status.RESOLVED, Ticket.Status.CLOSED );

    @SuppressWarnings( "LineLength" )
    @Override
    public void validate( Ticket ticket )
    {
        ticketRepository
                .findById( ticket.getId() )
                .ifPresent(
                        currentTicket ->
                        {
                            Ticket.Status ticketStatus = ticket.getStatus();

                            if ( UNEDITABLE_STATUSES.contains( ticketStatus ) )
                            {
                                throw new TicketUpdateNotAllowedForStatusException(
                                        "Você não pode atualizar um ticket que esteja em progresso, resolvido ou fechado."
                                );
                            }
                        }
                );
    }
}
