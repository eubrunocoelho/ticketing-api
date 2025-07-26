package com.eubrunocoelho.ticketing.controller;

import com.eubrunocoelho.ticketing.dto.category.CategoriesResponseDto;
import com.eubrunocoelho.ticketing.dto.category.CategoryCreateDto;
import com.eubrunocoelho.ticketing.dto.category.CategoryListDto;
import com.eubrunocoelho.ticketing.dto.category.CategoryResponseDto;
import com.eubrunocoelho.ticketing.dto.category.CategoryUpdateDto;
import com.eubrunocoelho.ticketing.entity.Categories;
import com.eubrunocoelho.ticketing.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tickets/categories")
@RequiredArgsConstructor
public class CategoryController {

    private static final String SCREEN_LABEL = "Ticketing API - [%s] [%s]";

    private final CategoryService categoryService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody @Valid CategoryCreateDto categoryCreateDTO) {
        Categories category = categoryService.createCategory(categoryCreateDTO);

        String label = String.format(
                SCREEN_LABEL, "", ""
        );

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(
                        category.getId()
                )
                .toUri();

        CategoryResponseDto responseDto = new CategoryResponseDto(
                label,
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getPriority().name()
        );

        return ResponseEntity.created(location).body(responseDto);
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CategoriesResponseDto> findAll() {
        List<CategoryListDto> categories = categoryService.findAll();

        String label = String.format(
                SCREEN_LABEL, "", ""
        );

        CategoriesResponseDto responseDto = new CategoriesResponseDto(
                label,
                categories
        );

        return ResponseEntity.ok().body(responseDto);
    }


    @PatchMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable long id, @RequestBody @Valid CategoryUpdateDto categoryUpdateDto) {
        Categories category = categoryService.updateCategory(id, categoryUpdateDto);

        String label = String.format(
                SCREEN_LABEL, "", ""
        );

        CategoryResponseDto responseDto = new CategoryResponseDto(
                label,
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getPriority().name()
        );

        return ResponseEntity.ok().body(responseDto);
    }
}
