package com.eubrunocoelho.ticketing.service.category;

import com.eubrunocoelho.ticketing.dto.category.CategoryCreateDto;
import com.eubrunocoelho.ticketing.dto.category.CategoryResponseDto;
import com.eubrunocoelho.ticketing.dto.category.CategoryUpdateDto;
import com.eubrunocoelho.ticketing.entity.Category;
import com.eubrunocoelho.ticketing.exception.entity.DataBindingViolationException;
import com.eubrunocoelho.ticketing.mapper.CategoryMapper;
import com.eubrunocoelho.ticketing.repository.CategoryRepository;
import com.eubrunocoelho.ticketing.exception.entity.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService
{
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    public CategoryResponseDto createCategory( CategoryCreateDto categoryCreateDTO )
    {
        Category category = categoryMapper.toEntity( categoryCreateDTO );
        Category createdCategory = categoryRepository.save( category );

        return categoryMapper.toDto( createdCategory );
    }

    @Transactional( readOnly = true )
    public List<CategoryResponseDto> findAll()
    {
        return categoryRepository
                .findAll()
                .stream()
                .map( categoryMapper::toDto )
                .toList();
    }

    @Transactional( readOnly = true )
    public CategoryResponseDto findById( Long id )
    {
        Category category = categoryRepository
                .findById( id )
                .orElseThrow( () ->
                        new ObjectNotFoundException(
                                "Categoria não encontrada. {id}: " + id
                        )
                );

        return categoryMapper.toDto( category );
    }

    @Transactional
    public CategoryResponseDto updateCategory(
            Long id,
            CategoryUpdateDto categoryUpdateDto
    )
    {
        Category category = categoryRepository
                .findById( id )
                .orElseThrow( () ->
                        new ObjectNotFoundException(
                                "Categoria não encontrada. {id}: " + id
                        )
                );

        categoryMapper.updateCategoryFromDto( categoryUpdateDto, category );
        Category updatedCategory = categoryRepository.save( category );

        return categoryMapper.toDto( updatedCategory );
    }

    @Transactional
    public void deleteCategory( Long id )
    {
        Category category = categoryRepository
                .findById( id )
                .orElseThrow( () ->
                        new ObjectNotFoundException(
                                "Categoria não encontrada. {id}: " + id
                        )
                );

        try
        {
            categoryRepository.deleteById( category.getId() );
        }
        catch ( DataIntegrityViolationException ex )
        {
            throw new DataBindingViolationException(
                    "Não é possível excluir pois há entidades relacionadas."
            );
        }
    }
}
