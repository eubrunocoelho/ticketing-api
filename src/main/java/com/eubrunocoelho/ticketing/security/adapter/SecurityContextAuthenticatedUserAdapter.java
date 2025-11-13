package com.eubrunocoelho.ticketing.security.adapter;

import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.security.principal.AuthenticatedUser;
import com.eubrunocoelho.ticketing.service.user.commom.GetAuthenticatedUserUseCase;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextAuthenticatedUserAdapter implements GetAuthenticatedUserUseCase
{
    @Override
    public User getAuthenticatedUser()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (
                authentication != null
                        && authentication.getPrincipal() instanceof AuthenticatedUser authenticatedUser
        )
        {
            return authenticatedUser.getUser();
        }

        throw new IllegalStateException( "Usuário não autenticado." );
    }
}
