package com.eubrunocoelho.ticketing.dto;

import com.eubrunocoelho.ticketing.dto.contract.ResponseDtoInterface;

public record CategoryResponseDto(

        String label,

        Long id,

        String name,

        String description,

        String priority
) implements ResponseDtoInterface {
}
