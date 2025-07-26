package com.eubrunocoelho.ticketing.service;

import com.eubrunocoelho.ticketing.dto.CategoryCreateDto;
import com.eubrunocoelho.ticketing.dto.CategoryResponseDto;
import com.eubrunocoelho.ticketing.entity.Categories;
import com.eubrunocoelho.ticketing.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.eubrunocoelho.ticketing.util.EnumUtil.getEnumValueOrNull;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private static final String SCREEN_LABEL = "Ticketing API - [%s] [%s]";

    private final CategoryRepository categoryRepository;
    private final LoginUtilityService loginUtilityService;

    public CategoryResponseDto createCategory(CategoryCreateDto categoryCreateDTO) {
        String label = String.format(
                SCREEN_LABEL,
                "",
                ""
        );

        Categories category = new Categories();

        category.setName(categoryCreateDTO.name());
        category.setDescription(categoryCreateDTO.description());
        category.setPriority(
                getEnumValueOrNull(Categories.Priority.class, categoryCreateDTO.priority())
        );

        Categories newCategory = categoryRepository.save(category);

        CategoryResponseDto responseDto = new CategoryResponseDto(
                label,
                newCategory.getId(),
                newCategory.getName(),
                newCategory.getDescription(),
                newCategory.getPriority().name()
        );

        return responseDto;
    }
}
