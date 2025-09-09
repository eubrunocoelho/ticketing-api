package com.eubrunocoelho.ticketing.dto.ticket;

public record TicketFilterDto(

        String status,

        String search,

        Long category,

        String user
) {
}
