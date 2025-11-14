package com.eubrunocoelho.ticketing.service.reply.validation.strategy;

import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.exception.business.TicketClosedReplyNotAllowedException;
import com.eubrunocoelho.ticketing.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order( 3 )
public class TicketClosedCannotReply implements ReplyValidationStrategy
{
    private final TicketRepository ticketRepository;

    @Override
    public void validate( Ticket ticket, User loggedUser )
    {
        ticketRepository
                .findById( ticket.getId() )
                .ifPresent(
                        currentTicket ->
                        {
                            if ( currentTicket.getStatus() == Ticket.Status.CLOSED )
                            {
                                throw new TicketClosedReplyNotAllowedException(
                                        "Você não pode responder um ticket que está fechado."
                                );
                            }
                        }
                );
    }
}
