package com.eubrunocoelho.ticketing.service.reply.validation.strategy;

import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.exception.business.SelfReplyNotAllowedException;
import com.eubrunocoelho.ticketing.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order( 2 )
public class LastReplyOwnerCannotReply implements ReplyValidationStrategy
{
    private final ReplyRepository replyRepository;

    @Override
    public void validate( Ticket ticket, User loggedUser )
    {
        replyRepository.findTopByTicketIdOrderByCreatedAtDesc( ticket.getId() )
                .ifPresent(
                        lastReply ->
                        {
                            boolean isReplyOwner = lastReply.getCreatedUser().getId().equals( loggedUser.getId() );

                            if ( isReplyOwner )
                            {
                                throw new SelfReplyNotAllowedException(
                                        "Você não pode responder sua própria resposta."
                                );
                            }
                        }
                );
    }
}
