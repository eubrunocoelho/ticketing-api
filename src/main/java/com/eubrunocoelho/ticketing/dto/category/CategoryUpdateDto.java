package com.eubrunocoelho.ticketing.dto.category;

import com.eubrunocoelho.ticketing.annotation.validation.ValidEnum;
import com.eubrunocoelho.ticketing.entity.Categories;
import jakarta.validation.constraints.Size;

public record CategoryUpdateDto(
        @Size(min = 4, message = "O valor para \"name\" deve ter no mínimo 4 caracteres.")
        @Size(max = 255, message = "O valor para \"name\" deve ter no máximo 255 caracteres.")
        String name,

        @Size(min = 16, message = "O valor para \"description\" deve ter no mínimo 16 caracteres.")
        @Size(max = 255, message = "O valor para \"description\" deve ter no máximo 255 caracteres.")
        String description,

        @ValidEnum(enumClass = Categories.Priority.class, message = "O valor de \"priority\" deve ser LOW, MEDIUM ou HIGH.")
        String priority
) {
}
