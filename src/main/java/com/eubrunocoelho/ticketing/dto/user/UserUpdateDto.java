package com.eubrunocoelho.ticketing.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateDto(
        @NotBlank( message = "O valor para \"password\" é obrigatório." )
        @Size( min = 8, message = "O valor para \"password\" deve ter no mínimo 8 caracteres." )
        @Size( max = 32, message = "O valor para \"password\" deve ter no máximo 32 caracteres." )
        String password
)
{
}
