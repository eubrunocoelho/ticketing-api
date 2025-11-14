package com.eubrunocoelho.ticketing.service.reply.config.strategy;

import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.repository.ReplyRepository;
import com.eubrunocoelho.ticketing.service.reply.config.ReplyConfigStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order( 3 )
public class SubsequentReplyConfig implements ReplyConfigStrategy
{
    private final ReplyRepository replyRepository;

    @Override
    public boolean applies( Ticket ticket, User loggedUser )
    {
        return replyRepository.existsByTicketId( ticket.getId() );
    }

    @Override
    public void configure( Reply reply, Ticket ticket, User loggedUser )
    {
        replyRepository
                .findTopByTicketIdOrderByCreatedAtDesc( ticket.getId() )
                .ifPresent(
                        lastReply ->
                        {
                            reply.setParent( lastReply );
                            reply.setRespondedToUser( lastReply.getCreatedUser() );
                        }

                );
    }
}
