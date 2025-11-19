package com.eubrunocoelho.ticketing.service.reply.config.strategy;

import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.repository.ReplyRepository;
import com.eubrunocoelho.ticketing.service.reply.config.ReplyConfigStrategy;
import com.eubrunocoelho.ticketing.service.reply.config.helper.ReplyConfigHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order( 1 )
public class FirstReplyConfig implements ReplyConfigStrategy
{
    private final ReplyConfigHelper replyConfigHelper;

    @Override
    public boolean applies( Ticket ticket, User loggedUser )
    {
        return !replyConfigHelper.existsReply( ticket.getId() );
    }

    @Override
    public void configure( Reply reply, Ticket ticket, User loggedUser )
    {
        reply.setParent( null );
        reply.setRespondedToUser( ticket.getUser() );
    }
}
