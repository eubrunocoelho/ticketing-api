package com.eubrunocoelho.ticketing.service.reply.config.helper;

import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StaffInterchangeHelper
{
    private final ReplyConfigHelper buildReplyHelper;

    public void configureReplyStaffToUser(
            Reply reply,
            Reply lastReplyByUserRole,
            User.Role lastReplyUserRole ,
            User loggedUser
    )
    {
        if (
                lastReplyByUserRole != null
                        && buildReplyHelper.isStaff( lastReplyUserRole )
                        && buildReplyHelper.isStaff( loggedUser.getRole() )
        )
        {
            reply.setParent( lastReplyByUserRole );
            reply.setRespondedToUser( lastReplyByUserRole.getTicket().getUser() );

            return ;
        }
    }

    public void configureReplyStaffToUser(
            Ticket ticket,
            Reply reply,
            Reply lastReplyByUserRole,
            User.Role lastReplyUserRole,
            User loggedUser
    )
    {
        if (
                lastReplyByUserRole == null
                        && buildReplyHelper.isStaff( lastReplyUserRole )
                        && buildReplyHelper.isStaff( loggedUser.getRole() )
        )
        {
            reply.setParent( null );
            reply.setRespondedToUser( ticket.getUser() );

            return ;
        }
    }
}
