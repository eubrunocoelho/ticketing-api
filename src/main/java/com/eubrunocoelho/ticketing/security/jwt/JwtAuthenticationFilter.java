package com.eubrunocoelho.ticketing.security.jwt;

import com.eubrunocoelho.ticketing.service.jwt.JwtAuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter
{
    private final JwtAuthenticationService jwtAuthenticationService;
    private final HandlerExceptionResolver handlerExceptionResolver;

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
        try
        {
            jwtAuthenticationService.processToken( request );
        }
        catch ( Exception ex )
        {
            handlerExceptionResolver.resolveException( request, response, null, ex );

            return ;
        }

        filterChain.doFilter( request, response );
    }
}
