package com.eubrunocoelho.ticketing.dto.user;

public record UserFilterDto(
        String search,

        String role,

        String status
)
{
}
