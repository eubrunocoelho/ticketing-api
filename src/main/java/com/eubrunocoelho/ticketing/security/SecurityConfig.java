package com.eubrunocoelho.ticketing.security;

import com.eubrunocoelho.ticketing.security.filter.InactiveUserFilter;
import com.eubrunocoelho.ticketing.security.filter.JwtAuthenticationFilter;
import com.eubrunocoelho.ticketing.security.jwt.JwtAccessDeniedHandler;
import com.eubrunocoelho.ticketing.security.jwt.JwtAuthEntryPoint;
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

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true
)
@RequiredArgsConstructor
public class SecurityConfig
{
    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final InactiveUserFilter inactiveUserFilter;
    private final UserPrincipalService userPrincipalService;

    @Bean
    @Order( 1 )
    public SecurityFilterChain securityFilterChain( HttpSecurity httpSecurity )
            throws Exception
    {
        httpSecurity
                .csrf(
                        csrf -> csrf.disable()
                )
                .authorizeHttpRequests(
                        auth ->
                                auth
                                        // Swagger / Springdoc
                                        .requestMatchers(
                                                "/swagger-ui/**",
                                                "/swagger-ui.html",
                                                "/v3/api-docs/**",
                                                "/v3/api-docs",
                                                "/webjars/**"
                                        ).permitAll()
                                        .requestMatchers( HttpMethod.POST, "/auth" ).permitAll()
                                        .requestMatchers( HttpMethod.POST, "/users" ).permitAll()
                                        .anyRequest().authenticated()
                )
                .sessionManagement(
                        session ->
                                session
                                        .sessionCreationPolicy( SessionCreationPolicy.STATELESS )
                )
                .exceptionHandling(
                        exception ->
                                exception
                                        .authenticationEntryPoint( jwtAuthEntryPoint )
                                        .accessDeniedHandler( jwtAccessDeniedHandler )
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .addFilterAfter(
                        inactiveUserFilter,
                        JwtAuthenticationFilter.class
                );

        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService()
    {
        return userPrincipalService::findMatch;
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
