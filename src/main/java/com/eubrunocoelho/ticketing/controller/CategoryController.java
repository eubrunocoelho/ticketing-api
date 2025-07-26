package com.eubrunocoelho.ticketing.controller;

import com.eubrunocoelho.ticketing.dto.CategoryCreateDto;
import com.eubrunocoelho.ticketing.dto.CategoryResponseDto;
import com.eubrunocoelho.ticketing.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/tickets/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CategoryResponseDto> create(@RequestBody @Valid CategoryCreateDto categoryCreateDTO) {
        CategoryResponseDto responseDto = categoryService.createCategory(categoryCreateDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(
                        responseDto.id()
                )
                .toUri();

        return ResponseEntity.created(location).body(responseDto);
    }
}
