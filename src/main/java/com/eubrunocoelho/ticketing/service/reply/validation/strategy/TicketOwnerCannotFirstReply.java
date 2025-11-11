package com.eubrunocoelho.ticketing.service.reply.validation.strategy;

import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.exception.business.SelfReplyNotAllowedException;
import com.eubrunocoelho.ticketing.repository.ReplyRepository;
import com.eubrunocoelho.ticketing.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order( 1 )
public class TicketOwnerCannotFirstReply implements ReplyValidationStrategy
{
    private final TicketRepository ticketRepository;
    private final ReplyRepository replyRepository;

    @Override
    public void validate( Ticket ticket, User loggedUser )
    {
        boolean existsReply = replyRepository.existsByTicketId( ticket.getId() );

        if ( existsReply )
        {
            return ;
        }

        ticketRepository
                .findById( ticket.getId() )
                .ifPresent(
                        currentTicket ->
                        {
                            boolean isTicketOwner = currentTicket.getUser().getId().equals( loggedUser.getId() );

                            if ( isTicketOwner )
                            {
                                throw new SelfReplyNotAllowedException(
                                        "Você não pode responder diretamente seu próprio ticket."
                                );
                            }
                        }
                );
    }
}
