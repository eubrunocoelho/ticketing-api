package com.eubrunocoelho.ticketing.service.reply.config.helper;

import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ReplyConfigHelper
{
    private final ReplyRepository replyRepository;

    private static final Set<User.Role> STAFF_ROLES = Set
            .of( User.Role.ROLE_ADMIN, User.Role.ROLE_STAFF );

    public boolean isStaff( User.Role role )
    {
        return STAFF_ROLES.contains( role );
    }

    public Optional<Reply> findLastReply( Long ticketId )
    {
        return replyRepository.findTopByTicketIdOrderByCreatedAtDesc( ticketId );
    }

    public Optional<Reply> findLastReplyByUserRole( Long ticketId, User.Role role )
    {
        return replyRepository
                .findTopByTicketIdAndCreatedUserRoleOrderByCreatedAtDesc( ticketId, role );
    }

    public boolean existsReply( Long ticketId )
    {
        return replyRepository.existsByTicketId( ticketId );
    }
}
