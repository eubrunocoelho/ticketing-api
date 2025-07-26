package com.eubrunocoelho.ticketing.service;

import com.eubrunocoelho.ticketing.dto.AuthDto;
import com.eubrunocoelho.ticketing.dto.AuthResponseDto;
import com.eubrunocoelho.ticketing.entity.Users;
import com.eubrunocoelho.ticketing.service.exception.CredentialsInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String SCREEN_LABEL = "Ticketing API - [%s] [%s]";

    private final UserService userService;
    private final JwtUtilityService jwtUtilityService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponseDto authenticate(AuthDto authDto) {
        String label = String.format(
                SCREEN_LABEL,
                "",
                ""
        );

        Users user = userService.findByUsername(authDto.username());

        if (user == null
                || !passwordEncoder.matches(authDto.password(), user.getPassword())
        ) {
            throw new CredentialsInvalidException("\"username\" ou \"password\" inválidos.");
        }

        Map<String, String> claims = new HashMap<>();
        claims.put("role", user.getRole().name());

        String authToken = jwtUtilityService.generateToken(
                claims,
                user.getUsername(),
                1000 * 60 * 60 * 24
        );

        AuthResponseDto responseDto = new AuthResponseDto(
                label,
                authToken,
                user.getUsername(),
                user.getRole().name()
        );

        return responseDto;
    }
}
