package com.eubrunocoelho.ticketing.service.reply.strategy;

import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.service.reply.strategy.helper.BuildReplyHelper;
import com.eubrunocoelho.ticketing.service.reply.strategy.helper.StaffInterchangeConfigurator;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Order( 2 )
public class StaffInterchangeReply implements ReplyStrategy
{
    private final BuildReplyHelper buildReplyHelper;
    private final StaffInterchangeConfigurator staffInterchangeConfigurator;

    @Override
    public boolean applies( Ticket ticket, User loggedUser )
    {
        Optional<Reply> lastReply = buildReplyHelper.findLastReply( ticket.getId() );

        return lastReply
                .filter(
                        reply ->
                                buildReplyHelper.isStaff( reply.getCreatedUser().getRole() )
                                        && buildReplyHelper.isStaff( loggedUser.getRole() )
                )
                .isPresent();
    }

    @Override
    public void configure( Reply reply, Ticket ticket, User loggedUser )
    {
        Optional<Reply> lastReply = buildReplyHelper.findLastReply( ticket.getId() );

        lastReply.ifPresentOrElse(
                lastReplyPresent ->
                {
                    Optional<Reply> lastReplyByUserRole = buildReplyHelper.findLastReplyByUserRole(
                            ticket.getId(),
                            User.Role.ROLE_USER
                    );

                    User.Role lastReplyUserRole = lastReplyPresent.getCreatedUser().getRole();

                    staffInterchangeConfigurator.configureReplyStaffToUser(
                            reply,
                            lastReplyByUserRole.orElse( null ),
                            lastReplyUserRole,
                            loggedUser
                    );
                    staffInterchangeConfigurator.configureReplyStaffToUser(
                            ticket,
                            reply,
                            lastReplyByUserRole.orElse( null ),
                            lastReplyUserRole,
                            loggedUser
                    );
                },
                () ->
                {
                    return ;
                }
        );
    }
}
