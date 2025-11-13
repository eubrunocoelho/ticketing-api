package com.eubrunocoelho.ticketing.security.principal;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class AuthenticatedUser
        extends org.springframework.security.core.userdetails.User
{
    private com.eubrunocoelho.ticketing.entity.User user;

    public AuthenticatedUser(
            com.eubrunocoelho.ticketing.entity.User user,
            Collection<? extends GrantedAuthority> authorities
    )
    {
        super( user.getUsername(), user.getPassword(), authorities );

        this.user = user;
    }

    public AuthenticatedUser(
            com.eubrunocoelho.ticketing.entity.User user,
            boolean enabled,
            boolean accountNonExpired,
            boolean credentialsNonExpired,
            boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities
    )
    {
        super(
                user.getUsername(),
                user.getPassword(),
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                authorities
        );
    }

}
