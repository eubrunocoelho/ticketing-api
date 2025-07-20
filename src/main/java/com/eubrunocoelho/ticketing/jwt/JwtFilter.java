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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtilityService jwtUtilityService;
    private final LoginUtilityService loginUtilityService;
    private final HandlerExceptionResolver handlerExceptionResolver;

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
        extractToken(request)
                .filter(token -> !jwtUtilityService.isTokenExpired(token))
                .map(jwtUtilityService::getUsername)
                .filter(username -> !isAlreadyAuthenticated())
                .ifPresent(username -> authenticateUser(username, request));
    }

    private Optional<String> extractToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Authorization"))
                .filter(header -> header.startsWith("Bearer "))
                .map(header -> header.substring(7));
    }

    private boolean isAlreadyAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }

    private void authenticateUser(String username, HttpServletRequest request) {
        logger.info("Autenticando usuário: {}", username);

        UserDetails userDetails = loginUtilityService.findMatch(username);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
