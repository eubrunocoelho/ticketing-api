package com.eubrunocoelho.ticketing.service.reply.validation;

import com.eubrunocoelho.ticketing.entity.Reply;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.exception.business.SelfReplyNotAllowedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ReplyValidationService
{
    private static final Set<User.Role> STAFF_ROLES = Set.of(
            User.Role.ROLE_ADMIN,
            User.Role.ROLE_STAFF
    );

    public void validate( Reply reply, User loggedUser )
    {
        User.Role loggedUserRole = loggedUser.getRole();

        if ( STAFF_ROLES.contains( loggedUserRole ) )
        {
            return;
        }

        User respondedToUser = reply.getRespondedToUser();

        if ( loggedUser.getId().equals( respondedToUser.getId() ) )
        {
            throw new SelfReplyNotAllowedException(
                    "Você não pode responder diretamente seu próprio ticket ou sua própria resposta."
            );
        }

        return;
    }
}
