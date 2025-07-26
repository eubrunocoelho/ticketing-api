package com.eubrunocoelho.ticketing.service;

import com.eubrunocoelho.ticketing.dto.CategoryCreateDto;
import com.eubrunocoelho.ticketing.dto.CategoryResponseDto;
import com.eubrunocoelho.ticketing.dto.CategoryUpdateDto;
import com.eubrunocoelho.ticketing.entity.Categories;
import com.eubrunocoelho.ticketing.mapper.CategoryMapper;
import com.eubrunocoelho.ticketing.repository.CategoryRepository;
import com.eubrunocoelho.ticketing.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.eubrunocoelho.ticketing.util.EnumUtil.getEnumValueOrNull;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public Categories createCategory(CategoryCreateDto categoryCreateDTO) {
        Categories category = new Categories();

        category.setName(categoryCreateDTO.name());
        category.setDescription(categoryCreateDTO.description());
        category.setPriority(
                getEnumValueOrNull(Categories.Priority.class, categoryCreateDTO.priority())
        );

        return categoryRepository.save(category);
    }

    public Categories findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Categoria não encontrada. {id}: " + id)
        );
    }

    public Categories updateCategory(Long id, CategoryUpdateDto categoryUpdateDto) {
        Categories category = findById(id);

        categoryMapper.updateCategoryFromDto(categoryUpdateDto, category);

        if (categoryUpdateDto.priority() != null) {
            category.setPriority(
                    getEnumValueOrNull(Categories.Priority.class, categoryUpdateDto.priority())
            );
        }

        return categoryRepository.save(category);
    }
}
