package com.eubrunocoelho.ticketing.service.auth;

import com.eubrunocoelho.ticketing.dto.auth.SignInRequestDto;
import com.eubrunocoelho.ticketing.dto.auth.AuthResponseDto;
import com.eubrunocoelho.ticketing.dto.user.UserResponseDto;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.exception.auth.InvalidCredentialsException;
import com.eubrunocoelho.ticketing.exception.entity.ObjectNotFoundException;
import com.eubrunocoelho.ticketing.mapper.UserMapper;
import com.eubrunocoelho.ticketing.security.jwt.JwtProvider;
import com.eubrunocoelho.ticketing.mapper.AuthMapper;
import com.eubrunocoelho.ticketing.service.auth.validation.CredentialValidationService;
import com.eubrunocoelho.ticketing.service.user.UserPrincipalService;
import com.eubrunocoelho.ticketing.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Validated
@RequiredArgsConstructor
public class AuthService
{
    private final UserService userService;
    private final UserPrincipalService userPrincipalService;
    private final JwtProvider jwtProvider;
    private final CredentialValidationService credentialValidationService;
    private final AuthMapper authMapper;
    private final UserMapper userMapper;

    @Value( "${com.eubrunocoelho.ticketing.jwt.expiration_days}" )
    private Long jwtExpirationDays;

    public AuthResponseDto authenticate( @Valid SignInRequestDto signInRequestDto
    )
    {
        User user;

        try
        {
            user = userService.findByUsernameOrEmail( signInRequestDto.username() );
        }
        catch ( ObjectNotFoundException ex )
        {
            throw new InvalidCredentialsException( "Credenciais inválidas." );
        }

        credentialValidationService.validate( user, signInRequestDto.password() );

        String authToken = generateAuthToken( user );

        return authMapper.toDto( user, authToken );
    }

    public UserResponseDto authenticatedUser()
    {
        User authenticatedUser = userPrincipalService.getLoggedInUser();

        return userMapper.toDto( authenticatedUser );
    }

    public String generateAuthToken( User user )
    {
        Map<String, String> claims = new HashMap<>();
        claims.put( "role", user.getRole().name() );

        return jwtProvider.generateToken(
                claims,
                user.getUsername(),
                TimeUnit.DAYS.toMillis( jwtExpirationDays )
        );
    }
}
