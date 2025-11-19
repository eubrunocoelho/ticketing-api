package com.eubrunocoelho.ticketing.dto.ticket;

import com.eubrunocoelho.ticketing.entity.Ticket;
import com.eubrunocoelho.ticketing.validation.annotation.ValidEnum;
import jakarta.validation.constraints.NotBlank;

public record TicketStatusUpdateDto(
        @NotBlank( message = "O valor para \"status\" é obrigatório." )
        @ValidEnum(
                enumClass = Ticket.Status.class,
                message = "O valor para \"status\" deve ser OPEN, IN_PROGRESS, RESOLVED ou CLOSED."
        )
        String status
)
{
}
