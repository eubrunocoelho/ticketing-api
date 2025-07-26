package com.eubrunocoelho.ticketing.dto.category;

public record CategoryListDto(

        Long id,

        String name,

        String description,

        String priority
) {
}
