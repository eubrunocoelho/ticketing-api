package com.eubrunocoelho.ticketing.dto.user;

import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.validation.annotation.ValidEnum;
import jakarta.validation.constraints.NotBlank;

public record UserRoleUpdateDto(
        @NotBlank( message = "O valor para \"role\" é obrigatório." )
        @ValidEnum(
                enumClass = User.Role.class,
                message = "O valor para \"role\" deve ser ROLE_ADMIN, ROLE_STAFF ou ROLE_USER."
        )
        String role
)
{
}
