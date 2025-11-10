package com.eubrunocoelho.ticketing.service.reply.strategy;

import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.service.reply.strategy.helper.ReplyRoleHelper;
import com.eubrunocoelho.ticketing.service.user.UserPrincipalService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Order( 2 )
public class StaffInterchangeStrategy implements ReplyStrategy
{
    private final UserPrincipalService userPrincipalService;
    private final ReplyRoleHelper roleHelper;

    @Override
    public boolean applies( Long ticketId )
    {
        User loggedUser = userPrincipalService.getLoggedInUser();
        Optional<Reply> lastReply = roleHelper.findLastReply( ticketId );

        return lastReply
                .filter(
                        reply ->
                                roleHelper.isStaff( reply.getCreatedUser().getRole() )
                                        && roleHelper.isStaff( loggedUser.getRole() )
                )
                .isPresent();
    }

    @Override
    public void configure( Reply reply, Long ticketId, Ticket ticket )
    {
        User loggedUser = userPrincipalService.getLoggedInUser();
        Optional<Reply> lastReply = roleHelper.findLastReply( ticketId );
        Optional<Reply> lastUserReply = roleHelper.findLastReplyByUserRole( ticketId, User.Role.ROLE_USER );

        if ( lastReply.isEmpty() )
        {
            return ;
        }

        User.Role lastRole = lastReply.get().getCreatedUser().getRole();

        if (
                lastUserReply.isPresent()
                        && roleHelper.isStaff( lastRole )
                        && roleHelper.isStaff( loggedUser.getRole() )
        )
        {
            reply.setParent( lastUserReply.get() );
            reply.setRespondedToUser( lastUserReply.get().getTicket().getUser() );

            return ;
        }

        if (
                lastUserReply.isEmpty()
                        && roleHelper.isStaff( lastRole )
                        && roleHelper.isStaff( loggedUser.getRole() )
        )
        {
            reply.setParent( null );
            reply.setRespondedToUser( ticket.getUser() );
        }
    }
}
