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

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<AuthResponseDto> authenticate(@RequestBody @Valid AuthDto authDto) {
        AuthResponseDto responseDto = authService.authenticate(authDto);
        
        return ResponseEntity.ok().body(responseDto);
    }
}
