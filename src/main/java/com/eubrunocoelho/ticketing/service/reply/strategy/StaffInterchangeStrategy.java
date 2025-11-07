package com.eubrunocoelho.ticketing.service.reply.strategy;

import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.repository.ReplyRepository;
import com.eubrunocoelho.ticketing.service.user.UserPrincipalService;
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
    private final UserPrincipalService userPrincipalService;
    private final ReplyRepository replyRepository;

    private static final Set<User.Role> STAFF_ROLES = Set.of(
            User.Role.ROLE_ADMIN,
            User.Role.ROLE_STAFF
    );

    @Override
    public boolean applies( Long ticketId )
    {
        // Pega `role` do usuário logado
        User.Role loggedUserRole = userPrincipalService.getLoggedInUser().getRole();

        // Existe uma resposta
        boolean existsReply = replyRepository.existsByTicketId( ticketId );

        // Última resposta
        Optional<Reply> lastReply = replyRepository.findTopByTicketIdOrderByCreatedAtDesc( ticketId );

        // Se existe resposta e a última resposta está presente
        if ( existsReply && lastReply.isPresent() )
        {
            // Pega `role` do usuário criador da última resposta
            User.Role replyCreatedUserRole = lastReply.get().getCreatedUser().getRole();

            // Se a última resposta é de um STAFF e o usuário logado é STAFF
            if ( STAFF_ROLES.contains( replyCreatedUserRole ) && STAFF_ROLES.contains( loggedUserRole ) )
            {
                // Retorna true
                return true;
            }
        }

        // Se não a regra não se aplica
        return false;
    }

    @Override
    public void configure( Reply reply, Long ticketId, Ticket ticket )
    {
        // Pega `role` do usuário logado
        User.Role loggedUserRole = userPrincipalService.getLoggedInUser().getRole();

        // Última resposta de um usuário ROLE_USER
        Optional<Reply> replyCreatedUserRoleUser = replyRepository
                .findTopByTicketIdAndCreatedUserRoleOrderByCreatedAtDesc( ticketId, User.Role.ROLE_USER );

        // Última resposta
        Optional<Reply> lastReply = replyRepository.findTopByTicketIdOrderByCreatedAtDesc( ticketId );
        User.Role lastReplyCreatedUserRole = lastReply.get().getCreatedUser().getRole();

        // Se existir uma resposta de um usuário ROLE_USER
        // E a última resposta é de um usuário STAFF
        // E o usuário logado é STAFF
        if (
                replyCreatedUserRoleUser.isPresent()
                        && STAFF_ROLES.contains( lastReplyCreatedUserRole )
                        && STAFF_ROLES.contains( loggedUserRole )
        )
        {
            reply.setParent( null );
            reply.setRespondedToUser( replyCreatedUserRoleUser.get().getTicket().getUser() );

            return;
        }

        // Se não existir uma resposta de um usuário ROLE_USER
        // E a última resposta é de um usuário STAFF
        // E o usuário logado é STAFF
        if (
                replyCreatedUserRoleUser.isEmpty()
                    && STAFF_ROLES.contains( lastReplyCreatedUserRole )
                    && STAFF_ROLES.contains( loggedUserRole )
        )
        {
            reply.setParent( null );
            reply.setRespondedToUser( replyCreatedUserRoleUser.get().getTicket().getUser() );

            return;
        }

        return;
    }
}
