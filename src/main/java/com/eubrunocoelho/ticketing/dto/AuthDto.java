package com.eubrunocoelho.ticketing.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthDto(

        @NotBlank(message = "O valor para \"username\" é obrigatório.")
        String username,

        @NotBlank(message = "O valor para \"password\" é obrigatório.")
        String password
) {
}
