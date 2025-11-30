package com.eubrunocoelho.ticketing.controller.category;

import com.eubrunocoelho.ticketing.controller.BaseController;
import com.eubrunocoelho.ticketing.dto.category.CategoryCreateDto;
import com.eubrunocoelho.ticketing.dto.category.CategoryResponseDto;
import com.eubrunocoelho.ticketing.dto.category.CategoryUpdateDto;
import com.eubrunocoelho.ticketing.dto.ResponseDto;
import com.eubrunocoelho.ticketing.service.category.CategoryService;
import com.eubrunocoelho.ticketing.util.ApiResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin( "*" )
@RestController
@RequestMapping( "/categories" )
@Tag( name = "Gerenciador de categorias." )
public class CategoryController extends BaseController
{
    private final CategoryService categoryService;

    public CategoryController(
            CategoryService categoryService,
            ApiResponseBuilder apiResponseBuilder
    )
    {
        super( apiResponseBuilder );

        this.categoryService = categoryService;
    }

    @Operation(
            summary = "Cadastrar categoria.",
            description = "Responsável por cadastrar categoria."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Categoria cadastrada.",
                            content = @Content( schema = @Schema( implementation = CategoryResponseDto.class ) )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Não autorizado.",
                            content = @Content( schema = @Schema() )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Erro de validação.",
                            content = @Content( schema = @Schema() )
                    )
            }
    )
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize( "@categoryPermission.canCreateCategory()" )
    public ResponseEntity<ResponseDto<CategoryResponseDto>> createCategory(
            @RequestBody CategoryCreateDto categoryCreateDTO
    )
    {
        CategoryResponseDto createdCategoryResponse = categoryService.createCategory(
                categoryCreateDTO
        );

        return createdResponse( createdCategoryResponse, createdCategoryResponse.id() );
    }

    @Operation(
            summary = "Encontrar determinada categoria.",
            description = "Responsável por encontrar determinada categoria."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Dados da categoria.",
                            content = @Content( schema = @Schema( implementation = CategoryResponseDto.class ) )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Não autorizado.",
                            content = @Content( schema = @Schema() )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Não autorizado.",
                            content = @Content( schema = @Schema() )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Categoria não encontrada.",
                            content = @Content( schema = @Schema() )
                    )
            }
    )
    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<CategoryResponseDto>> findCategory( @PathVariable Long id )
    {
        CategoryResponseDto categoryResponse = categoryService.findById( id );

        return okResponse( categoryResponse );
    }

    @Operation(
            summary = "Encontrar todas as categorias.",
            description = "Responsável por encontrar e listar todas as categorias."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de categorias.",
                            content = @Content( schema = @Schema( implementation = CategoryResponseDto.class ) )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Não autorizado.",
                            content = @Content( schema = @Schema() )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Não autorizado.",
                            content = @Content( schema = @Schema() )
                    )
            }
    )
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<List<CategoryResponseDto>>> findAllCategories()
    {
        List<CategoryResponseDto> categoriesResponse = categoryService.findAll();

        return okResponse( categoriesResponse );
    }

    @Operation(
            summary = "Atualizar categoria.",
            description = "Responsável por atualizar dados de uma determinada categoria."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Categoria atualizada.",
                            content = @Content( schema = @Schema( implementation = CategoryResponseDto.class ) )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Não autorizado.",
                            content = @Content( schema = @Schema() )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Categoria não encontrada.",
                            content = @Content( schema = @Schema() )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Erro de validação.",
                            content = @Content( schema = @Schema() )
                    )
            }
    )
    @PatchMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize( "@categoryPermission.canUpdateCategory()" )
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

    @Operation(
            summary = "Deletar categoria.",
            description = "Responsável por deletar uma determinada categoria."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Categoria deletada.",
                            content = @Content( schema = @Schema() )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Não autorizado.",
                            content = @Content( schema = @Schema() )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Não autorizado.",
                            content = @Content( schema = @Schema() )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Categoria não encontrada.",
                            content = @Content( schema = @Schema() )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Conflito no banco de dados.",
                            content = @Content( schema = @Schema() )
                    )
            }
    )
    @DeleteMapping(
            value = "/{id}"
    )
    @PreAuthorize( "@categoryPermission.canDeleteCategory()" )
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Long id
    )
    {
        categoryService.deleteCategory( id );

        return noContentResponse();
    }
}
