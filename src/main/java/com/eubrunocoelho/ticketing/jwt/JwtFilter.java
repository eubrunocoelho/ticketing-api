package com.eubrunocoelho.ticketing.jwt;

import com.eubrunocoelho.ticketing.service.JwtUtilityService;
import com.eubrunocoelho.ticketing.service.LoginUtilityService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtilityService jwtUtilityService;
    private final LoginUtilityService loginUtilityService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    // DEBUG
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            processToken(request);
        } catch (Exception ex) {
            handlerExceptionResolver.resolveException(
                    request,
                    response,
                    null,
                    ex
            );
        }

        filterChain.doFilter(request, response);
    }

    private void processToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        logger.info("Authorization Header: {}", authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.info("No Bearer Header, skip processing");

            return;
        }

        final String jwtToken = authHeader.substring(7);

        if (jwtUtilityService.isTokenExpired(jwtToken)) {
            logger.info("Token validity expired");

            return;
        }

        String username = jwtUtilityService.getUsername(jwtToken);

        if (username == null) {
            logger.info("No username found in JWT Token");

            return;
        }

        logger.info("Username found in JWT: " + username);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            logger.info("Already loggedin: " + username);

            return;
        }

        logger.info("Create authentication instance for {}", username);

        UserDetails userDetails = loginUtilityService.findMatch(username);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        authToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
