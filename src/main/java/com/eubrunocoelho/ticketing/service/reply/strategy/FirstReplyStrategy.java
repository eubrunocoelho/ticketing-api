package com.eubrunocoelho.ticketing.service.reply.strategy;

import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order( 1 )
public class FirstReplyStrategy implements ReplyStrategy
{
    private final ReplyRepository replyRepository;

    @Override
    public boolean applies( Long ticketId )
    {
        return !replyRepository.existsByTicketId( ticketId );
    }

    @Override
    public void configure( Reply reply, Long ticketId, Ticket ticket )
    {
        reply.setParent( null );
        reply.setRespondedToUser( ticket.getUser() );

        return;
    }
}
