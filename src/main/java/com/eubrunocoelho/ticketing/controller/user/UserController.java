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
import com.eubrunocoelho.ticketing.util.ResponseBuilder;
import com.eubrunocoelho.ticketing.util.sort.user.UserSortResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "/users" )
public class UserController extends BaseController
{
    private final UserService userService;
    private final UserSortResolver userSortResolver;

    public UserController(
            UserService userService,
            UserSortResolver userSortResolver,
            ResponseBuilder responseBuilder
    )
    {
        super( responseBuilder );

        this.userService = userService;
        this.userSortResolver = userSortResolver;
    }

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
