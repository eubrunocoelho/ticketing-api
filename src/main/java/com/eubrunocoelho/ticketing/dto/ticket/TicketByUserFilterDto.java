package com.eubrunocoelho.ticketing.dto.ticket;

public record TicketByUserFilterDto(
        String status,

        String search,

        Long category
)
{
}
