package com.eubrunocoelho.ticketing.service.reply.validation;

import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.service.reply.validation.create.ReplyCreateValidator;
import com.eubrunocoelho.ticketing.service.reply.validation.delete.ReplyDeleteValidator;
import com.eubrunocoelho.ticketing.service.reply.validation.update.ReplyUpdateValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyValidationService
{
    private final ReplyCreateValidator replyCreateValidator;
    private final ReplyUpdateValidator replyUpdateValidator;
    private final ReplyDeleteValidator replyDeleteValidator;

    public void createValidate( Ticket ticket, User loggedUser )
    {
        replyCreateValidator.execute( ticket, loggedUser );
    }

    public void updateValidate( Reply reply )
    {
        replyUpdateValidator.execute( reply );
    }

    public void deleteValidate( Reply reply )
    {
        replyDeleteValidator.execute( reply );
    }
}
