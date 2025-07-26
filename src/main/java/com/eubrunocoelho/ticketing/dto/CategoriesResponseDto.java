package com.eubrunocoelho.ticketing.dto;

import com.eubrunocoelho.ticketing.dto.contract.ResponseDtoInterface;

import java.util.List;

public record CategoriesResponseDto(

        String label,

        List<CategoryListDto> categories
) implements ResponseDtoInterface {
}
