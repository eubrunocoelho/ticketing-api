package com.eubrunocoelho.ticketing.controller.user;

import com.eubrunocoelho.ticketing.controller.BaseController;
import com.eubrunocoelho.ticketing.dto.ResponseDto;
import com.eubrunocoelho.ticketing.dto.user.UserCreateDto;
import com.eubrunocoelho.ticketing.dto.user.UserFilterDto;
import com.eubrunocoelho.ticketing.dto.user.UserResponseDto;
import com.eubrunocoelho.ticketing.dto.user.UserRoleUpdateDto;
import com.eubrunocoelho.ticketing.dto.user.UserStatusUpdateDto;
import com.eubrunocoelho.ticketing.dto.user.UserUpdateDto;
import com.eubrunocoelho.ticketing.service.user.UserService;
import com.eubrunocoelho.ticketing.util.PageableFactory;
import com.eubrunocoelho.ticketing.util.ApiResponseBuilder;
import com.eubrunocoelho.ticketing.util.sort.user.UserSortResolver;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin( "*" )
@RestController
@RequestMapping( "/users" )
@Tag( name = "Gerenciador de usuários." )
public class UserController extends BaseController
{
    private final UserService userService;
    private final UserSortResolver userSortResolver;

    public UserController(
            UserService userService,
            UserSortResolver userSortResolver,
            ApiResponseBuilder responseBuilder
    )
    {
        super( responseBuilder );

        this.userService = userService;
        this.userSortResolver = userSortResolver;
    }

    @Operation(
            summary = "Cadastrar usuário.",
            description = "Responsável por cadastrar usuário."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Usuário cadastrado.",
                            content = @Content( schema = @Schema( implementation = UserResponseDto.class ) )
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
    public ResponseEntity<ResponseDto<UserResponseDto>> create(
            @RequestBody UserCreateDto userCreateDto
    )
    {
        UserResponseDto createdUserResponse = userService.createUser( userCreateDto );

        return createdResponse( createdUserResponse, createdUserResponse.id() );
    }

    @Operation(
            summary = "Encontrar usuário.",
            description = "Responsável por encontrar um determinado usuário."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Dados do usuário.",
                            content = @Content( schema = @Schema( implementation = UserResponseDto.class ) )
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
                            description = "Usuário não encontrado.",
                            content = @Content( schema = @Schema() )
                    )
            }
    )
    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize( "@userPermission.canAccessUser(#id)" )
    public ResponseEntity<ResponseDto<UserResponseDto>> findUser( @PathVariable Long id )
    {
        UserResponseDto userResponse = userService.findById( id );

        return okResponse( userResponse );
    }

    @Operation(
            summary = "Encontrar todos os usuários.",
            description = "Responsável por encontrar todos os usuários."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de usuários.",
                            content = @Content( schema = @Schema( implementation = UserResponseDto.class ) )
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
    @PreAuthorize( "@userPermission.canAccessAllUsers()" )
    public ResponseEntity<ResponseDto<List<UserResponseDto>>> findAllUsers(
            UserFilterDto filter,
            Pageable pageable,
            @RequestParam( name = "sort", required = false ) String sortParam
    )
    {
        Sort sort = userSortResolver.resolve( sortParam );
        Pageable sortedPageable = PageableFactory.build( pageable, sort );

        Page<UserResponseDto> pageableUsersResponse = userService.findAllPaged(
                filter,
                sortedPageable
        );

        return okResponse( pageableUsersResponse );
    }

    @Operation(
            summary = "Atualizar usuário.",
            description = "Responsável por atualizar dados de um determinado usuário."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuário atualizado.",
                            content = @Content( schema = @Schema( implementation = UserResponseDto.class ) )
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
                            description = "Usuário não encontrado.",
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
    @PreAuthorize( "@userPermission.canUpdateUser(#id)" )
    public ResponseEntity<ResponseDto<UserResponseDto>> updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdateDto userUpdateDto
    )
    {
        UserResponseDto updatedUserResponse = userService.updateUser( id, userUpdateDto );

        return okResponse( updatedUserResponse );
    }

    @Operation(
            summary = "Atualizar cargo do usuário.",
            description = "Responsável por atualizar o cargo de um determinado usuário."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Cargo do usuário atualizado.",
                            content = @Content( schema = @Schema( implementation = UserResponseDto.class ) )
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
                            description = "Usuário não encontrado.",
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
            value = "/{id}/role",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize( "@userPermission.canUpdateUserRole(#id)" )
    public ResponseEntity<ResponseDto<UserResponseDto>> updateUserRole(
            @PathVariable Long id,
            @RequestBody UserRoleUpdateDto userRoleUpdateDto
    )
    {
        UserResponseDto updatedUserRoleResponse = userService.updateUserRole( id, userRoleUpdateDto );

        return okResponse( updatedUserRoleResponse );
    }

    @Operation(
            summary = "Atualizar status do usuário.",
            description = "Responsável por atualizar o status de um determinado usuário."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Status do usuário atualizado.",
                            content = @Content( schema = @Schema( implementation = UserResponseDto.class ) )
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
                            description = "Usuário não encontrado.",
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
            value = "/{id}/status",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize( "@userPermission.canUpdateUserStatus(#id)" )
    public ResponseEntity<ResponseDto<UserResponseDto>> updateUserStatus(
            @PathVariable Long id,
            @RequestBody UserStatusUpdateDto userStatusUpdateDto
    )
    {
        UserResponseDto updatedUserStatusResponse = userService.updateUserStatus( id, userStatusUpdateDto );

        return okResponse( updatedUserStatusResponse );
    }

    @Operation(
            summary = "Deletar usuário.",
            description = "Responsável por deletar um determinado usuário."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Usuário deletado.",
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
                            description = "Usuário não encontrado.",
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
    @PreAuthorize( "@userPermission.canDeleteUser(#id)" )
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id
    )
    {
        userService.deleteUser( id );

        return noContentResponse();
    }
}
