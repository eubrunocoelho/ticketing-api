package com.eubrunocoelho.ticketing.service.reply.validation.create;

import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReplyCreateValidator
{
    private final List<ReplyCreateValidationRule>  strategies;

    public void execute( Ticket ticket, User loggedUser )
    {
        strategies
                .forEach( strategy -> strategy.validate( ticket, loggedUser ) );
    }
}
