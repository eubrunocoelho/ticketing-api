package com.eubrunocoelho.ticketing.dto.category;

import com.eubrunocoelho.ticketing.dto.contract.ResponseDtoInterface;

import java.util.List;

public record CategoriesResponseDto(

        String label,

        List<CategoryListDto> categories
) implements ResponseDtoInterface {
}
