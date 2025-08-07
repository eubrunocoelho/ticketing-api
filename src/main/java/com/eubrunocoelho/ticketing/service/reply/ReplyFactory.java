package com.eubrunocoelho.ticketing.service.reply;

import com.eubrunocoelho.ticketing.dto.reply.ReplyCreateDto;
import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReplyFactory {

    private final ReplyConfigurator replyConfigurator;

    public Reply buildReply(Long ticketId, ReplyCreateDto dto, User loggedUser, Ticket ticket) {
        Reply reply = new Reply();
        reply.setTicket(ticket);
        reply.setContent(dto.content());
        reply.setCreatedUser(loggedUser);

        replyConfigurator.configure(reply, ticketId, ticket);

        return reply;
    }
}
