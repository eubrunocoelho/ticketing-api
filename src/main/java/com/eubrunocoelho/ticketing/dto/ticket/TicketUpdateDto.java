package com.eubrunocoelho.ticketing.dto.ticket;

import com.eubrunocoelho.ticketing.validation.annotation.ExistsCategoryId;
import jakarta.validation.constraints.Size;

public record TicketUpdateDto(
        @ExistsCategoryId
        Long category,

        @Size( min = 6, message = "O valor para \"title\" deve ter no mínimo 6 caracteres." )
        @Size( max = 255, message = "O valor para \"title\" deve ter no máximo 255 caracteres." )
        String title,

        @Size( min = 32, message = "O valor para \"content\" deve ter no mínimo 32 caracteres." )
        @Size( max = 65535, message = "O valor para \"content\" deve ter no máximo 65535 caracteres." )
        String content
)
{
}
