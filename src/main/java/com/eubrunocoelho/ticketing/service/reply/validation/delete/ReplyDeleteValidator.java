package com.eubrunocoelho.ticketing.service.reply.validation.delete;

import com.eubrunocoelho.ticketing.entity.Reply;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReplyDeleteValidator
{
    private final List<ReplyDeleteValidationRule> strategies;

    public void execute( Reply reply )
    {
        strategies
                .forEach( strategy -> strategy.validate( reply ) );
    }
}
