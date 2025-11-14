package com.eubrunocoelho.ticketing.service.reply.validation.delete;

import com.eubrunocoelho.ticketing.entity.Reply;

public interface ReplyDeleteValidationRule
{
    void validate( Reply reply );
}
