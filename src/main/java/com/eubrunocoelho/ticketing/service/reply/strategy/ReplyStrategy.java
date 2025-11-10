package com.eubrunocoelho.ticketing.service.reply.strategy;

import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;

public interface ReplyStrategy
{
    boolean applies( Ticket ticket, User loggedUser );

    void configure( Reply reply, Ticket ticket, User loggedUser );
}
