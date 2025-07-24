package com.eubrunocoelho.ticketing.service;

import com.eubrunocoelho.ticketing.dto.CategoryCreateDTO;
import com.eubrunocoelho.ticketing.entity.Categories;
import com.eubrunocoelho.ticketing.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.eubrunocoelho.ticketing.util.EnumUtil.getEnumValueOrNull;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Categories createCategory(CategoryCreateDTO categoryCreateDTO) {
        Categories category = new Categories();

        category.setName(categoryCreateDTO.name());
        category.setDescription(categoryCreateDTO.description());
        category.setPriority(
                getEnumValueOrNull(Categories.Priority.class, categoryCreateDTO.priority())
        );

        return categoryRepository.save(category);
    }
}
