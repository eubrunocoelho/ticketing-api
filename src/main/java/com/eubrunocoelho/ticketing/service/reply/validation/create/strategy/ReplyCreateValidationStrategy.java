package com.eubrunocoelho.ticketing.service.reply.validation.create.strategy;

import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;

public interface ReplyCreateValidationStrategy
{
    void validate( Ticket ticket, User loggedUser );
}
