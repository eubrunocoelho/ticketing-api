package com.eubrunocoelho.ticketing.service.reply.validation;

import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.service.reply.validation.strategy.ReplyValidationExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyValidationService
{
    private final ReplyValidationExecutor validationExecutor;

    public void validate( Ticket ticket, User loggedUser )
    {
        validationExecutor.execute( ticket, loggedUser );
    }
}
