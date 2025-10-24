package com.eubrunocoelho.ticketing.dto.reply;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ReplyCreateDto(
        @NotBlank( message = "O valor para \"content\" é obrigatório." )
        @Size( min = 32, message = "O valor para \"content\" deve ter no mínimo 32 caracteres." )
        @Size( max = 65535, message = "O valor para \"content\" deve ter no máximo 65535 caracteres." )
        String content
)
{
}
