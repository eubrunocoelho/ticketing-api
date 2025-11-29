package com.eubrunocoelho.ticketing.security.filter;

import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.security.principal.AuthenticatedUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class InactiveUserFilter extends OncePerRequestFilter
{
    @Override
    protected void doFilterInternal(
            @NonNull
            HttpServletRequest request,

            @NonNull
            HttpServletResponse response,

            @NonNull
            FilterChain filterChain
    ) throws ServletException, IOException
    {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (
                authentication != null
                        && authentication.getPrincipal() instanceof AuthenticatedUser authenticatedUser
        )
        {
            User user = authenticatedUser.getUser();

            if ( user.getStatus() == User.Status.INACTIVE )
            {
                response.sendError( HttpServletResponse.SC_UNAUTHORIZED );

                return ;
            }
        }

        filterChain.doFilter( request, response );
    }
}
