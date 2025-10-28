package com.eubrunocoelho.ticketing.dto.user;

public record UserResponseDto(

        Long id,

        String username,

        String email,

        String role
)
{
}
