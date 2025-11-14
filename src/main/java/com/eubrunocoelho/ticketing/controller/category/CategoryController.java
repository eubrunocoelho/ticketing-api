package com.eubrunocoelho.ticketing.controller.category;

import com.eubrunocoelho.ticketing.controller.BaseController;
import com.eubrunocoelho.ticketing.dto.category.CategoryCreateDto;
import com.eubrunocoelho.ticketing.dto.category.CategoryResponseDto;
import com.eubrunocoelho.ticketing.dto.category.CategoryUpdateDto;
import com.eubrunocoelho.ticketing.dto.ResponseDto;
import com.eubrunocoelho.ticketing.service.category.CategoryService;
import com.eubrunocoelho.ticketing.util.ResponseBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "/categories" )
public class CategoryController extends BaseController
{
    private final CategoryService categoryService;

    public CategoryController(
            CategoryService categoryService,
            ResponseBuilder responseBuilder
    )
    {
        super( responseBuilder );

        this.categoryService = categoryService;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize( "@categorySecurity.canCreateCategory()" )
    public ResponseEntity<ResponseDto<CategoryResponseDto>> createCategory(
            @RequestBody CategoryCreateDto categoryCreateDTO
    )
    {
        CategoryResponseDto createdCategoryResponse = categoryService.createCategory(
                categoryCreateDTO
        );

        return createdResponse( createdCategoryResponse, createdCategoryResponse.id() );
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<CategoryResponseDto>> findCategory( @PathVariable Long id )
    {
        CategoryResponseDto categoryResponse = categoryService.findById( id );

        return okResponse( categoryResponse );
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<List<CategoryResponseDto>>> findAllCategories()
    {
        List<CategoryResponseDto> categoriesResponse = categoryService.findAll();

        return okResponse( categoriesResponse );
    }

    @PatchMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize( "@categorySecurity.canUpdateCategory()" )
    public ResponseEntity<ResponseDto<CategoryResponseDto>> updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryUpdateDto categoryUpdateDto
    )
    {
        CategoryResponseDto updatedCategoryResponse = categoryService.updateCategory(
                id,
                categoryUpdateDto
        );

        return okResponse( updatedCategoryResponse );
    }

    @DeleteMapping(
            value = "/{id}"
    )
    @PreAuthorize( "@categorySecurity.canDeleteCategory()" )
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Long id
    )
    {
        categoryService.deleteCategory( id );

        return noContentResponse();
    }
}
