package com.eubrunocoelho.ticketing.service.reply.validation.strategy;

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
public class TicketOwnerCannotFirstReplyValidation implements ReplyValidationStrategy
{
    private final TicketRepository ticketRepository;
    private final ReplyRepository replyRepository;

    @Override
    public void validate( Long ticketId, User loggedUser )
    {
        boolean existsReply = replyRepository.existsByTicketId( ticketId );

        if ( existsReply )
        {
            return ;
        }

        ticketRepository
                .findById( ticketId )
                .ifPresent(
                        ticket ->
                        {
                            boolean isTicketOwner = ticket.getUser().getId().equals( loggedUser.getId() );

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
