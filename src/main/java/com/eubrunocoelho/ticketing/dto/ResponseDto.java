package com.eubrunocoelho.ticketing.dto;

public record ResponseDto<T>(

        String label,

        T data
) implements DefaultResponseDto<T> {
}
