package com.eubrunocoelho.ticketing.dto.auth;

public record AuthResponseDto(

        String authToken,

        String username,

        String role
) {
}
