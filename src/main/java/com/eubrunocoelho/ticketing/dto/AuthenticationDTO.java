package com.eubrunocoelho.ticketing.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(

        @NotBlank(message = "username")
        String username,

        @NotBlank(message = "password")
        String password
) {
}
