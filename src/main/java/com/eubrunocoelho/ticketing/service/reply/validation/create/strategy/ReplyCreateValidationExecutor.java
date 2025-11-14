package com.eubrunocoelho.ticketing.service.reply.validation.create.strategy;

import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReplyCreateValidationExecutor
{
    private final List<ReplyCreateValidationStrategy>  strategies;

    public void execute( Ticket ticket, User loggedUser )
    {
        strategies
                .forEach( strategy -> strategy.validate( ticket, loggedUser ) );
    }
}
