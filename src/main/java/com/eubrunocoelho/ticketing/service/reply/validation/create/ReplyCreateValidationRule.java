package com.eubrunocoelho.ticketing.service.reply.validation.create;

import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;

public interface ReplyCreateValidationRule
{
    void validate( Ticket ticket, User loggedUser );
}
