package com.eubrunocoelho.ticketing.controller;

import com.eubrunocoelho.ticketing.dto.AuthDto;
import com.eubrunocoelho.ticketing.dto.AuthResponseDto;
import com.eubrunocoelho.ticketing.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final String SCREEN_LABEL = "Ticketing API - [%s] [%s]";

    private final AuthService authService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<AuthResponseDto> authenticate(@RequestBody @Valid AuthDto authDto) {
        Map<String, String> authenticate = authService.authenticate(authDto);

        String label = String.format(
                SCREEN_LABEL, "", ""
        );

        AuthResponseDto responseDto = new AuthResponseDto(
                label,
                authenticate.get("authToken"),
                authenticate.get("username"),
                authenticate.get("role")
        );

        String authorization = "Bearer " + responseDto.authToken();

        return ResponseEntity
                .ok()
                .header("Authorization", authorization)
                .body(responseDto);
    }
}
