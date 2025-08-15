package com.eubrunocoelho.ticketing.dto.user;

import com.eubrunocoelho.ticketing.validation.annotation.UniqueEmail;
import com.eubrunocoelho.ticketing.validation.annotation.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserCreateDto(

        @NotBlank(message = "O valor para \"email\" é obrigatório.")
        @Email(message = "O valor para \"email\" deve ser um endereço de e-mail válido.")
        @Size(max = 128, message = "O valor para \"email\" está muito longo.")
        @UniqueEmail
        String email,

        @NotBlank(message = "O valor para \"username\" é obrigatório.")
        @Size(min = 8, message = "O valor para \"username\" deve ter no mínimo 8 caracteres.")
        @Size(max = 32, message = "O valor para \"username\" deve ter no máximo 32 caracteres.")
        @Pattern(
                regexp = "^(?!\\\\d)[a-zA-Z_][a-zA-Z0-9_]*$",
                message = "O valor para \"username\" deve começar com letra ou _ (underscore), conter apenas letras, números e _ (underscore), e não pode iniciar com número."
        )
        @UniqueUsername
        String username,

        @NotBlank(message = "O valor para \"password\" é obrigatório.")
        @Size(min = 8, message = "O valor para \"password\" deve ter no mínimo 8 caracteres.")
        @Size(max = 32, message = "O valor para \"password\" deve ter no máximo 32 caracteres.")
        String password
) {
}
