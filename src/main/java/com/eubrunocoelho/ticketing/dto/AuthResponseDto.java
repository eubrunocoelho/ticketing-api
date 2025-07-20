package com.eubrunocoelho.ticketing.dto;

public record AuthResponseDto(

        String authToken,

        String username,

        String role
) {
}
