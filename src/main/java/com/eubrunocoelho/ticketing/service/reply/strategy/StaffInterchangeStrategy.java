package com.eubrunocoelho.ticketing.service.reply.strategy;

import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Order( 2 )
public class StaffInterchangeStrategy implements ReplyStrategy
{
    private final ReplyRepository replyRepository;

    private static final Set<User.Role> STAFF_ROLES = Set.of(
            User.Role.ROLE_ADMIN,
            User.Role.ROLE_STAFF
    );

    @Override
    public boolean applies( Long ticketId )
    {
        if ( !replyRepository.existsByTicketId( ticketId ) )
        {
            return false;
        }

        Optional<Reply> lastReplyOptional = replyRepository
                .findTopByTicketIdOrderByCreatedAtDesc( ticketId );

        if ( lastReplyOptional.isEmpty() )
        {
            return false;
        }

        User.Role lastReplyCreatorRole = lastReplyOptional.get().getCreatedUser().getRole();

        return STAFF_ROLES.contains( lastReplyCreatorRole );
    }
    @Override
    public void configure( Reply reply, Long ticketId, Ticket ticket )
    {
        if ( !STAFF_ROLES.contains( reply.getCreatedUser().getRole() ) )
        {
            throw new IllegalStateException(
                    "Regra de Intercâmbio Staff/Admin não se aplica ao criador atual. Buscando a próxima estratégia."
            );
        }

        replyRepository
                .findTopByTicketIdAndCreatedUserRoleOrderByCreatedAtDesc(
                        ticketId, User.Role.ROLE_USER
                )
                .ifPresentOrElse(
                        lastUserReply ->
                        {
                            reply.setParent( lastUserReply );
                            reply.setRespondedToUser( ticket.getUser() );
                        },
                        () ->
                        {
                            reply.setParent( null );
                            reply.setRespondedToUser( ticket.getUser() );
                        }
                );
    }
}
