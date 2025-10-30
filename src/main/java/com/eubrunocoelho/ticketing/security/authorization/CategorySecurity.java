package com.eubrunocoelho.ticketing.security.authorization;

import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.service.user.UserPrincipalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component( "categorySecurity" )
@RequiredArgsConstructor
public class CategorySecurity
{
    private final UserPrincipalService userPrincipalService;

    public boolean canCreateCategory()
    {
        User loggedUser = userPrincipalService.getLoggedInUser();

        return loggedUser.getRole() == User.Role.ROLE_ADMIN
                || loggedUser.getRole() == User.Role.ROLE_STAFF;
    }

    public boolean canUpdateCategory()
    {
        User loggedUser = userPrincipalService.getLoggedInUser();

        return loggedUser.getRole() == User.Role.ROLE_ADMIN
                || loggedUser.getRole() == User.Role.ROLE_STAFF;
    }

    public boolean canDeleteCategory()
    {
        User loggedUser = userPrincipalService.getLoggedInUser();

        return loggedUser.getRole() == User.Role.ROLE_ADMIN
                || loggedUser.getRole() == User.Role.ROLE_STAFF;
    }
}
