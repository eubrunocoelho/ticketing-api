package com.eubrunocoelho.ticketing.service.auth;

import com.eubrunocoelho.ticketing.dto.auth.SignInRequestDto;
import com.eubrunocoelho.ticketing.dto.auth.AuthResponseDto;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.exception.auth.InvalidCredentialsException;
import com.eubrunocoelho.ticketing.exception.entity.ObjectNotFoundException;
import com.eubrunocoelho.ticketing.security.jwt.JwtProvider;
import com.eubrunocoelho.ticketing.mapper.AuthMapper;
import com.eubrunocoelho.ticketing.service.auth.validation.CredentialValidationService;
import com.eubrunocoelho.ticketing.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService
{
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final CredentialValidationService credentialValidationService;
    private final AuthMapper authMapper;

    public AuthResponseDto authenticate( SignInRequestDto signInRequestDto
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

    // Add constant for expireInterval
    public String generateAuthToken( User user )
    {
        Map<String, String> claims = new HashMap<>();
        claims.put( "role", user.getRole().name() );

        return jwtProvider.generateToken(
                claims,
                user.getUsername(),
                1000 * 60 * 60 * 24
        );
    }
}
