package com.eubrunocoelho.ticketing.security.authorization;

import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.repository.UserRepository;
import com.eubrunocoelho.ticketing.service.user.UserPrincipalService;
import org.springframework.stereotype.Component;

@Component( "userSecurity" )
public class UserSecurity extends BaseSecurity
{
    private final UserRepository userRepository;

    public UserSecurity(
            UserPrincipalService userPrincipalService,
            UserRepository userRepository
    )
    {
        super( userPrincipalService );

        this.userRepository = userRepository;
    }

    public boolean canAccessUser( Long userId )
    {
        if ( isAdminOrStaff( getLoggedUser() ) )
        {
            return true;
        }

        return userRepository
                .findById( userId )
                .map(
                        user -> isOwner( getLoggedUser(), user.getId() )
                )
                .orElse( false );
    }

    public boolean canAccessAllUsers()
    {
        return isAdminOrStaff( getLoggedUser() );
    }

    public boolean canUpdateUser( Long userId )
    {
        if ( isAdminOrStaff( getLoggedUser() ) )
        {
            return true;
        }

        return userRepository
                .findById( userId )
                .map(
                        user -> isOwner( getLoggedUser(), user.getId() )
                )
                .orElse( false );
    }

    public boolean canUpdateUserRole( Long userId )
    {
        boolean loggedUserIsAdmin = isAdmin( getLoggedUser() );
        boolean ownerUserIsNotAdmin = userRepository
                .findById( userId )
                .map(
                        user -> !isAdmin( user )
                )
                .orElse( false );

        return loggedUserIsAdmin && ownerUserIsNotAdmin;
    }

    public boolean canDeleteUser( Long userId )
    {
        boolean loggedUserIsAdmin = isAdmin( getLoggedUser() );
        boolean ownerUserIsNotAdmin = userRepository
                .findById( userId )
                .map(
                        user -> !isAdmin( user )
                )
                .orElse( false );

        return loggedUserIsAdmin && ownerUserIsNotAdmin;
    }
}
