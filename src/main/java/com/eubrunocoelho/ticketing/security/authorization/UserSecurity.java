package com.eubrunocoelho.ticketing.security.authorization;

import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.repository.UserRepository;
import com.eubrunocoelho.ticketing.service.user.UserPrincipalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component( "userSecurity" )
@RequiredArgsConstructor
public class UserSecurity
{
    private final UserPrincipalService userPrincipalService;
    private final UserRepository userRepository;

    public boolean canAccessUser( Long userId )
    {
        User loggedUser = userPrincipalService.getLoggedInUser();

        if ( loggedUser.getRole() == User.Role.ROLE_ADMIN
                || loggedUser.getRole() == User.Role.ROLE_STAFF
        )
        {
            return true;
        }

        return userRepository
                .findById( userId )
                .map(
                        user -> user.getId().equals( loggedUser.getId() )
                )
                .orElse( false );
    }

    public boolean canAccessAllUsers()
    {
        User loggedUser = userPrincipalService.getLoggedInUser();

        return loggedUser.getRole() == User.Role.ROLE_ADMIN
                || loggedUser.getRole() == User.Role.ROLE_STAFF;
    }

    public boolean canUpdateUser( Long userId )
    {
        User loggedUser = userPrincipalService.getLoggedInUser();

        if ( loggedUser.getRole() == User.Role.ROLE_ADMIN
                || loggedUser.getRole() == User.Role.ROLE_STAFF
        )
        {
            return true;
        }

        return userRepository
                .findById( userId )
                .map(
                        user -> user.getId().equals( loggedUser.getId() )
                )
                .orElse( false );
    }
}
