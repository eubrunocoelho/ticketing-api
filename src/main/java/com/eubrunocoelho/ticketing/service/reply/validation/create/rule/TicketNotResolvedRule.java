package com.eubrunocoelho.ticketing.service.reply.validation.create.rule;

import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.exception.business.TicketResolvedReplyNotAllowedException;
import com.eubrunocoelho.ticketing.repository.TicketRepository;
import com.eubrunocoelho.ticketing.service.reply.validation.create.ReplyCreateValidationRule;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order( 4 )
public class TicketNotResolvedRule implements ReplyCreateValidationRule
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
                            if ( currentTicket.getStatus() == Ticket.Status.RESOLVED )
                            {
                                throw new TicketResolvedReplyNotAllowedException(
                                        "Você não pode responder um ticket que esteja resolvido ou finalizado."
                                );
                            }
                        }
                );
    }
}
