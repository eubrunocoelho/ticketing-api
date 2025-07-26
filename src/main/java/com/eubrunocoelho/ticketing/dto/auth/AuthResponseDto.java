package com.eubrunocoelho.ticketing.dto.auth;

public record AuthResponseDto(

        String label,

        String authToken,

        String username,

        String role
) {
}
