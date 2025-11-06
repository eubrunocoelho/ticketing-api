package com.eubrunocoelho.ticketing.service.reply.strategy;

import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReplyConfigurator
{
    private final List<ReplyStrategy> strategies;

    public void configure( Reply reply, Long ticketId, Ticket ticket )
    {
        strategies.stream()
                .filter( strategy -> strategy.applies( ticketId ) )
                .findFirst()
                .orElseThrow(
                        () -> new IllegalStateException( "Estratégia não aplicável." )
                )
                .configure( reply, ticketId, ticket );
    }
}
