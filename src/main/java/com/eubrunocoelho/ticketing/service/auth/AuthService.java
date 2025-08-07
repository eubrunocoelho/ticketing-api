package com.eubrunocoelho.ticketing.service.auth;

import com.eubrunocoelho.ticketing.dto.auth.AuthDto;
import com.eubrunocoelho.ticketing.dto.auth.AuthResponseDto;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.exception.auth.InvalidCredentialsException;
import com.eubrunocoelho.ticketing.jwt.JwtUtility;
import com.eubrunocoelho.ticketing.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtUtility jwtUtilityService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponseDto authenticate(AuthDto authDto) {
        User user = userService.findByUsername(authDto.username());

        if (user == null
                || !passwordEncoder.matches(authDto.password(), user.getPassword())
        ) {
            throw new InvalidCredentialsException("\"username\" ou \"password\" inválidos.");
        }

        Map<String, String> claims = new HashMap<>();
        claims.put("role", user.getRole().name());
        String authToken = jwtUtilityService.generateToken(
                claims,
                user.getUsername(),
                1000 * 60 * 60 * 24
        );

        return new AuthResponseDto(
                authToken,
                user.getUsername(),
                user.getRole().name()
        );
    }
}
