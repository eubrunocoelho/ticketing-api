package com.eubrunocoelho.ticketing.security;

import com.eubrunocoelho.ticketing.security.filter.JwtAuthenticationFilter;
import com.eubrunocoelho.ticketing.service.user.UserPrincipalService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Inspect
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
@RequiredArgsConstructor
public class SecurityConfig
{
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserPrincipalService userPrincipalService;

    @Bean
    @Order( 1 )
    public SecurityFilterChain basicAuthSecurityFilterChain( HttpSecurity httpSecurity ) throws Exception
    {
        return httpSecurity
                .csrf( csrf -> csrf.disable() )
                .authorizeHttpRequests(
                        request ->
                        {
                            request.requestMatchers( "/auth/**" )
                                    .permitAll();
                            request.requestMatchers( HttpMethod.POST, "/users" )
                                    .permitAll();
                            request.anyRequest()
                                    .authenticated();
                        }
                )
                .sessionManagement( session -> session.sessionCreationPolicy
                        ( SessionCreationPolicy.STATELESS )
                )
                .addFilterBefore( jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class )
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService()
    {
        UserDetailsService userDetailsService =
                ( username ->
                    {
                        return userPrincipalService.findMatch( username );
                    }
                );

        return userDetailsService;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
