package com.eubrunocoelho.ticketing.service.reply.validation;

import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.exception.business.SelfReplyNotAllowedException;
import org.springframework.stereotype.Component;

@Component
public class ReplyValidationService
{
    public void validate( Reply reply, User loggedUser )
    {
        User respondedTo = reply.getRespondedToUser();

        if ( respondedTo != null && loggedUser.getId().equals( respondedTo.getId() ) )
        {
            throw new SelfReplyNotAllowedException(
                    "Você não pode responder diretamente seu próprio ticket ou sua própria resposta."
            );
        }
    }
}
