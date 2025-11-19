package com.eubrunocoelho.ticketing.security.permission;

import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.service.user.UserPrincipalService;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public abstract class BasePermission
{
    /**
     * Responsável por usuário principal autenticado {@link User}
     */
    protected final UserPrincipalService userPrincipalService;

    /**
     * Cargos relacionados a equipe {@link User.Role}
     */
    protected static final Set<User.Role> STAFF_ROLES = Set
            .of( User.Role.ROLE_ADMIN, User.Role.ROLE_STAFF );

    protected User getLoggedUser()
    {
        return userPrincipalService.getLoggedInUser();
    }

    protected boolean isAdmin( User user )
    {
        return user.getRole() == User.Role.ROLE_ADMIN;
    }

    protected boolean isAdminOrStaff( User user )
    {
        return STAFF_ROLES.contains( user.getRole() );
    }

    protected boolean isOwner( User user, Long ownerId )
    {
        return user.getId().equals( ownerId );
    }
}
