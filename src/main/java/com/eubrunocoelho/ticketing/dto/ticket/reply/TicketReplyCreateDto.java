package com.eubrunocoelho.ticketing.dto.ticket.reply;

import com.eubrunocoelho.ticketing.annotation.validation.ExistsTicketId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TicketReplyCreateDto(


        @NotNull(message = "O valor para \"ticket\" é obrigatório.")
        @ExistsTicketId
        Long ticket,

        @NotBlank(message = "O valor para \"content\" é obrigatório.")
        @Size(min = 32, message = "O valor para \"content\" deve ter no mínimo 32 caracteres.")
        @Size(max = 65535, message = "O valor para \"content\" deve ter no máximo 65535 caracteres.")
        String content
) {
}
