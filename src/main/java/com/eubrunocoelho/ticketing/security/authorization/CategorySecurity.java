package com.eubrunocoelho.ticketing.security.authorization;

import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.service.user.UserPrincipalService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component( "categorySecurity" )
@RequiredArgsConstructor
public class CategorySecurity
{
    private final UserPrincipalService userPrincipalService;

    public boolean canCreate( Authentication authentication )
    {
        User loggedUser = userPrincipalService.getLoggedInUser();

        return loggedUser.getRole() == User.Role.ROLE_ADMIN
                || loggedUser.getRole() == User.Role.ROLE_STAFF;
    }

    public boolean canUpdate( Authentication authentication )
    {
        User loggedUser = userPrincipalService.getLoggedInUser();

        return loggedUser.getRole() == User.Role.ROLE_ADMIN
                || loggedUser.getRole() == User.Role.ROLE_STAFF;
    }

    public boolean canDelete( Authentication authentication )
    {
        User loggedUser = userPrincipalService.getLoggedInUser();

        return loggedUser.getRole() == User.Role.ROLE_ADMIN
                || loggedUser.getRole() == User.Role.ROLE_STAFF;
    }
}
