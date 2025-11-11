package com.eubrunocoelho.ticketing.security.authorization;

import com.eubrunocoelho.ticketing.service.user.UserPrincipalService;
import org.springframework.stereotype.Component;

@Component( "categorySecurity" )
public class CategorySecurity extends BaseSecurity
{
    protected CategorySecurity( UserPrincipalService userPrincipalService )
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
