package com.eubrunocoelho.ticketing.controller;

import com.eubrunocoelho.ticketing.dto.AuthDto;
import com.eubrunocoelho.ticketing.entity.Users;
import com.eubrunocoelho.ticketing.service.JwtUtilityService;
import com.eubrunocoelho.ticketing.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtilityService jwtUtilityService;

    @PostMapping()
    public ResponseEntity<?> authenticate (@RequestBody @Valid AuthDto authenticationDTO) {
        Users user = userService.findByUsername(authenticationDTO.username());

        if (user == null || !user.getPassword().equalsIgnoreCase(authenticationDTO.password())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        Map<String, String> claims = new HashMap<>();
        claims.put("role", user.getRole().name());

        String token = jwtUtilityService.generateToken(claims, user.getUsername(), 1000 * 60 * 60 * 24);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(
                        Map.of(
                                "authToken", token,
                                "username", user.getUsername(),
                                "role", user.getRole().name()
                        )
                );
    }
}
