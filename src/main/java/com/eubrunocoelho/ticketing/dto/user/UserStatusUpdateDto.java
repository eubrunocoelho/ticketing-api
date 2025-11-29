package com.eubrunocoelho.ticketing.dto.user;

import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.validation.annotation.ValidEnum;
import jakarta.validation.constraints.NotBlank;

public record UserStatusUpdateDto(
        @NotBlank( message = "O valor para \"status\" é obrigatório." )
        @ValidEnum(
                enumClass = User.Status.class,
                message = "O valor para \"status\" deve ser ACTIVE ou INACTIVE."
        )
        String status
)
{
}
