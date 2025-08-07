package com.eubrunocoelho.ticketing.service.reply;

import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.Ticket;

public interface ReplyStrategy {

    boolean applies(Long ticketId);

    void configure(Reply reply, Long ticketId, Ticket ticket);
}
