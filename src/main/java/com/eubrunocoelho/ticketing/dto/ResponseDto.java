package com.eubrunocoelho.ticketing.dto;

import com.eubrunocoelho.ticketing.dto.meta.MetaResponseDto;

public record ResponseDto<T>(
        String label,

        T data,

        MetaResponseDto meta
) implements DefaultResponseDto<T>
{
}
