package com.eubrunocoelho.ticketing.controller;

import com.eubrunocoelho.ticketing.dto.category.CategoryCreateDto;
import com.eubrunocoelho.ticketing.dto.category.CategoryResponseDto;
import com.eubrunocoelho.ticketing.dto.category.CategoryUpdateDto;
import com.eubrunocoelho.ticketing.dto.ResponseDto;
import com.eubrunocoelho.ticketing.service.category.CategoryService;
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
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController extends AbstractController {

    private final CategoryService categoryService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<CategoryResponseDto>> createCategory(
            @RequestBody @Valid CategoryCreateDto categoryCreateDTO
    ) {
        CategoryResponseDto categoryResponseDto = categoryService.createCategory(
                categoryCreateDTO
        );

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(
                        categoryResponseDto.id()
                )
                .toUri();

        ResponseDto<CategoryResponseDto> responseDto = new ResponseDto<>(
                getScreenLabel(true),
                categoryResponseDto
        );

        return ResponseEntity.created(location).body(responseDto);
    }


    @PatchMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<CategoryResponseDto>> updateCategory(
            @PathVariable Long id, @RequestBody @Valid CategoryUpdateDto categoryUpdateDto
    ) {
        CategoryResponseDto categoryResponseDto = categoryService.updateCategory(
                id,
                categoryUpdateDto
        );

        ResponseDto<CategoryResponseDto> responseDto = new ResponseDto<>(
                getScreenLabel(true),
                categoryResponseDto
        );

        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<CategoryResponseDto>> findCategory(@PathVariable Long id) {
        CategoryResponseDto categoryResponseDto = categoryService.findById(id);

        ResponseDto<CategoryResponseDto> responseDto = new ResponseDto<>(
                getScreenLabel(true),
                categoryResponseDto
        );

        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<List<CategoryResponseDto>>> findAllCategory() {
        List<CategoryResponseDto> listCategoryResponseDto = categoryService.findAll();

        ResponseDto<List<CategoryResponseDto>> responseDto = new ResponseDto<>(
                getScreenLabel(true),
                listCategoryResponseDto
        );

        return ResponseEntity.ok().body(responseDto);
    }
}
