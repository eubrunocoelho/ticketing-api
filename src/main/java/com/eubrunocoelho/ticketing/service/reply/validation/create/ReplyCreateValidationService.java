package com.eubrunocoelho.ticketing.service.reply.validation.create;

import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.service.reply.validation.create.strategy.ReplyCreateValidationExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyCreateValidationService
{
    private final ReplyCreateValidationExecutor validationExecutor;

    public void validate( Ticket ticket, User loggedUser )
    {
        validationExecutor.execute( ticket, loggedUser );
    }
}
