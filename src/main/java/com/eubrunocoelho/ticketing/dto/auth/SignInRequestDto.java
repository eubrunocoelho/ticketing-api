package com.eubrunocoelho.ticketing.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record SignInRequestDto(
        @NotBlank( message = "O valor para \"username\" é obrigatório." )
        String username,

        @NotBlank( message = "O valor para \"password\" é obrigatório." )
        String password
)
{
}
