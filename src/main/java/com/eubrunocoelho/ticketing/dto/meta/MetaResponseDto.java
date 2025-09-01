package com.eubrunocoelho.ticketing.dto.meta;

public record MetaResponseDto(

        boolean firstPage,

        boolean lastPage,

        Integer numberOfElements,

        Integer pageNumber,

        Integer pageSize,

        Integer totalPages
) {
}
