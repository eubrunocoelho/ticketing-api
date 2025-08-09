package com.eubrunocoelho.ticketing.service.reply;

import com.eubrunocoelho.ticketing.dto.reply.ReplyCreateDto;
import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.mapper.ReplyMapper;
import com.eubrunocoelho.ticketing.service.reply.strategy.ReplyConfigurator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReplyFactory {

    private final ReplyMapper replyMapper;
    private final ReplyConfigurator replyConfigurator;

    public Reply buildReply(
            Long ticketId,
            ReplyCreateDto replyCreateDto,
            User loggedUser,
            Ticket ticket
    ) {
        Reply reply = replyMapper.toEntity(replyCreateDto, ticket, loggedUser);

        replyConfigurator.configure(reply, ticketId, ticket);

        return reply;
    }
}
