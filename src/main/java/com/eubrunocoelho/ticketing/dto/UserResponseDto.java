package com.eubrunocoelho.ticketing.dto;

import com.eubrunocoelho.ticketing.dto.contract.ResponseDtoInterface;

public record UserResponseDto(

        String label,

        Long id,

        String username,

        String email
) implements ResponseDtoInterface {
}
