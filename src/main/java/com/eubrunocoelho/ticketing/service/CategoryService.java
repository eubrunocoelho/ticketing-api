package com.eubrunocoelho.ticketing.service;

import com.eubrunocoelho.ticketing.dto.category.CategoryCreateDto;
import com.eubrunocoelho.ticketing.dto.category.CategoryResponseDto;
import com.eubrunocoelho.ticketing.dto.category.CategoryUpdateDto;
import com.eubrunocoelho.ticketing.entity.Category;
import com.eubrunocoelho.ticketing.mapper.CategoryMapper;
import com.eubrunocoelho.ticketing.repository.CategoryRepository;
import com.eubrunocoelho.ticketing.exception.entity.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryResponseDto createCategory(CategoryCreateDto categoryCreateDTO) {
        Category category = categoryMapper.toEntity(categoryCreateDTO);
        Category createdCategory = categoryRepository.save(category);

        return categoryMapper.toDto(createdCategory);
    }

    public CategoryResponseDto updateCategory(Long id, CategoryUpdateDto categoryUpdateDto) {
        Category category = findById(id);

        categoryMapper.updateCategoryFromDto(categoryUpdateDto, category);
        Category updatedCategory = categoryRepository.save(category);

        return categoryMapper.toDto(updatedCategory);
    }

    public List<CategoryResponseDto> findAll() {
        return categoryRepository
                .findAll()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Categoria não encontrada. {id}: " + id)
        );
    }
}
