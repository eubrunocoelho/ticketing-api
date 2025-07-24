package com.eubrunocoelho.ticketing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryCreateDTO(
        @NotBlank(message = "O valor para \"name\" é obrigatório.")
        @Size(min = 16, message = "O valor para \"name\" deve ter no mínimo 16 caracteres.")
        @Size(max = 255, message = "O valor para \"name\" deve ter no máximo 255 caracteres.")
        String name,

        @Size(min = 6, message = "O valor para \"name\" deve ter no mínimo 16 caracteres.")
        @Size(max = 255, message = "O valor para \"name\" deve ter no máximo 255 caracteres.")
        String description,

        String priority
) {
}
