package com.eubrunocoelho.ticketing.dto.category;

public record CategoryResponseDto(
        Long id,

        String name,

        String description,

        String priority
)
{
}
