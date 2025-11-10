package com.eubrunocoelho.ticketing.service.reply.validation.strategy;

import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.exception.business.SelfReplyNotAllowedException;
import com.eubrunocoelho.ticketing.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order( 2 )
public class LastReplyOwnerCannotReplyValidation implements ReplyValidationStrategy
{
    private final ReplyRepository replyRepository;

    @Override
    public void validate( Long ticketId, User loggedUser )
    {
        replyRepository.findTopByTicketIdOrderByCreatedAtDesc( ticketId )
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
