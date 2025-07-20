package com.eubrunocoelho.ticketing.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthDto(

        @NotBlank(message = "username")
        String username,

        @NotBlank(message = "password")
        String password
) {
}
