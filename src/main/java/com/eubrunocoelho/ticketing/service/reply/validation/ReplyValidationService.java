package com.eubrunocoelho.ticketing.service.reply.validation;

import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.service.reply.validation.create.ReplyCreateValidator;
import com.eubrunocoelho.ticketing.service.reply.validation.delete.ReplyDeleteValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyValidationService
{
    private final ReplyCreateValidator replyCreateValidator;
    private final ReplyDeleteValidator replyDeleteValidator;

    public void createValidate( Ticket ticket, User loggedUser )
    {
        replyCreateValidator.execute( ticket, loggedUser );
    }

    public void deleteValidate( Reply reply )
    {
        replyDeleteValidator.execute( reply );
    }
}
