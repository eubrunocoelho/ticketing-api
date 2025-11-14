package com.eubrunocoelho.ticketing.service.reply.config.strategy;

import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.service.reply.config.ReplyConfigStrategy;
import com.eubrunocoelho.ticketing.service.reply.config.helper.ReplyConfigHelper;
import com.eubrunocoelho.ticketing.service.reply.config.helper.StaffInterchangeHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Order( 2 )
public class StaffInterchangeConfig implements ReplyConfigStrategy
{
    private final ReplyConfigHelper buildReplyHelper;
    private final StaffInterchangeHelper staffInterchangeConfigurator;

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
