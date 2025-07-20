package com.eubrunocoelho.ticketing.dto;

import com.eubrunocoelho.ticketing.dto.contract.ResponseDtoInterface;

public record AuthResponseDto(

        String label,

        String authToken,

        String username,

        String role
) implements ResponseDtoInterface {
}
