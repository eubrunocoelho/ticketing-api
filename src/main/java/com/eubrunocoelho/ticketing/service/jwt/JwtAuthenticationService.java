package com.eubrunocoelho.ticketing.service.jwt;

import com.eubrunocoelho.ticketing.exception.jwt.JwtTokenExpiredException;
import com.eubrunocoelho.ticketing.security.jwt.JwtProvider;
import com.eubrunocoelho.ticketing.exception.jwt.JwtTokenMalformedException;
import com.eubrunocoelho.ticketing.service.user.UserPrincipalService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationService
{
    private final JwtProvider jwtProvider;
    private final UserPrincipalService userPrincipalService;

    public void processToken(
            HttpServletRequest request
    ) throws JwtTokenMalformedException, JwtTokenExpiredException
    {
        String token = extractToken( request );

        if ( token == null )
        {
            return ;
        }

        try
        {
            String username = jwtProvider.getUsername( token );

            if ( username != null && !isAlreadyAuthenticated() )
            {
                authenticateUser( username, request );
            }
        }
        catch ( MalformedJwtException ex )
        {
            throw new JwtTokenMalformedException(
                    "O token de acesso fornecido é inválido."
            );
        }
        catch ( ExpiredJwtException ex )
        {
            throw new JwtTokenExpiredException(
                    "O token de acesso fornecido expirou. Por favor, faça login novamente."
            );
        }
    }

    private String extractToken( HttpServletRequest request )
    {
        String header = request.getHeader( "Authorization" );

        if ( header == null || !header.startsWith( "Bearer " ) )
        {
            return null;
        }

        return header.substring( 7 );
    }

    private boolean isAlreadyAuthenticated()
    {
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }

    private void authenticateUser( String username, HttpServletRequest request )
    {
        UserDetails userDetails = userPrincipalService.findMatch( username );

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        authToken.setDetails(
                new WebAuthenticationDetailsSource()
                        .buildDetails( request )
        );

        SecurityContextHolder.getContext().setAuthentication( authToken );
    }
}
