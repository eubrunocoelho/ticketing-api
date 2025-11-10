package com.eubrunocoelho.ticketing.service.reply.validation.strategy;

import com.eubrunocoelho.ticketing.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReplyValidationExecutor
{
    private final List<ReplyValidationStrategy>  validations;

    public void execute( Long ticketId, User loggedUser )
    {
        validations.forEach(
                validation -> validation.validate( ticketId, loggedUser )
        );
    }
}
