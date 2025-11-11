package com.eubrunocoelho.ticketing.service.reply.strategy;

import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReplyConfigurator
{
    private final List<ReplyStrategy> strategies;

    public void configure( Reply reply, Ticket ticket, User loggedUser )
    {
        strategies
                .stream()
                .filter( strategy -> strategy.applies( ticket, loggedUser ) )
                .findFirst()
                .orElseThrow(
                        () -> new IllegalStateException(
                                "Estratégia para criação de respostas não aplicável."
                        )
                )
                .configure( reply, ticket, loggedUser );
    }
}
