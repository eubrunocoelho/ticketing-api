package com.eubrunocoelho.ticketing.service.reply.validation.delete;

import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.service.reply.validation.delete.strategy.ReplyDeleteValidationExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyDeleteValidationService
{
    private final ReplyDeleteValidationExecutor validationExecutor;

    public void validate( Reply reply )
    {
        validationExecutor.execute( reply );
    }
}
