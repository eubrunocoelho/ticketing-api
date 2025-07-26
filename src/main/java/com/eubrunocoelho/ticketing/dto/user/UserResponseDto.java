package com.eubrunocoelho.ticketing.dto.user;

import com.eubrunocoelho.ticketing.dto.contract.ResponseDtoInterface;

public record UserResponseDto(

        String label,

        Long id,

        String username,

        String email
) implements ResponseDtoInterface {
}
