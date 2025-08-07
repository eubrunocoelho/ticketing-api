package com.eubrunocoelho.ticketing.service.reply;

import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ReplyValidationService {

    public void validate(Reply reply, User loggedUser) {
        User respondedTo = reply.getRespondedToUser();

        if (respondedTo != null && loggedUser.getId().equals(respondedTo.getId())) {
            throw new IllegalArgumentException("loggedUser == respondedToUser");
        }
    }
}
