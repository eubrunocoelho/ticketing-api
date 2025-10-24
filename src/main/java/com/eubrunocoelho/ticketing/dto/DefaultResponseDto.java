package com.eubrunocoelho.ticketing.dto;

import com.eubrunocoelho.ticketing.dto.meta.MetaResponseDto;

public interface DefaultResponseDto<T>
{
    String label();

    T data();

    MetaResponseDto meta();
}
