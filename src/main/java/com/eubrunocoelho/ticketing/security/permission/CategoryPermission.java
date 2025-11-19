package com.eubrunocoelho.ticketing.security.permission;

import com.eubrunocoelho.ticketing.service.user.UserPrincipalService;
import org.springframework.stereotype.Component;

@Component( "categoryPermission" )
public class CategoryPermission extends BasePermission
{
    protected CategoryPermission( UserPrincipalService userPrincipalService )
    {
        super( userPrincipalService );
    }

    public boolean canCreateCategory()
    {
        return isAdminOrStaff( userPrincipalService.getLoggedInUser() );
    }

    public boolean canUpdateCategory()
    {
        return isAdminOrStaff( userPrincipalService.getLoggedInUser() );
    }

    public boolean canDeleteCategory()
    {
        return isAdminOrStaff( userPrincipalService.getLoggedInUser() );
    }
}
