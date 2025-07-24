package com.eubrunocoelho.ticketing.controller;

import com.eubrunocoelho.ticketing.dto.CategoryCreateDTO;
import com.eubrunocoelho.ticketing.entity.Categories;
import com.eubrunocoelho.ticketing.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping(
//            consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> create(@RequestBody @Valid CategoryCreateDTO categoryCreateDTO) {
        Categories createdCategory = categoryService.createCategory(categoryCreateDTO);

        return ResponseEntity.ok().body(null);
    }
}
