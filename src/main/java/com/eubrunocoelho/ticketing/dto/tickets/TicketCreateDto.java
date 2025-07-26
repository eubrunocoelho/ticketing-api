package com.eubrunocoelho.ticketing.dto.tickets;

import com.eubrunocoelho.ticketing.annotation.validation.ExistsCategoryId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TicketCreateDto(

        @NotBlank(message = "O valor para \"title\" é obrigatório.")
        @Size(min = 6, message = "O valor para \"title\" deve ter no mínimo 6 caracteres.")
        @Size(max = 255, message = "O valor para \"title\" deve ter no máximo 255 caracteres.")
        String title,

        @NotBlank(message = "O valor para \"description\" é obrigatório.")
        @Size(min = 32, message = "O valor para \"description\" deve ter no mínimo 32 caracteres.")
        @Size(max = 65535, message = "O valor para \"description\" deve ter no máximo 65535 caracteres.")
        String description,

        @NotNull(message = "O valor para \"categoryId\" é obrigatório.")
        @ExistsCategoryId
        Long categoryId
) {
}
