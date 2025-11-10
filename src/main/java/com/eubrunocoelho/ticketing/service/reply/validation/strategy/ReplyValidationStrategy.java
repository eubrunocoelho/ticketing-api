package com.eubrunocoelho.ticketing.service.reply.validation.strategy;

import com.eubrunocoelho.ticketing.entity.User;

public interface ReplyValidationStrategy
{
    void validate( Long ticketId, User loggedUser );
}
