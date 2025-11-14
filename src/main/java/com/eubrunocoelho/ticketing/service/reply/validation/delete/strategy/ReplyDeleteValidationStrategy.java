package com.eubrunocoelho.ticketing.service.reply.validation.delete.strategy;

import com.eubrunocoelho.ticketing.entity.Reply;

public interface ReplyDeleteValidationStrategy
{
    void validate( Reply reply );
}
