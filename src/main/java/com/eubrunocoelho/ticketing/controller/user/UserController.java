package com.eubrunocoelho.ticketing.controller.user;

import com.eubrunocoelho.ticketing.controller.BaseController;
import com.eubrunocoelho.ticketing.dto.ResponseDto;
import com.eubrunocoelho.ticketing.dto.user.UserCreateDto;
import com.eubrunocoelho.ticketing.dto.user.UserResponseDto;
import com.eubrunocoelho.ticketing.dto.user.UserUpdateDto;
import com.eubrunocoelho.ticketing.service.user.UserService;
import com.eubrunocoelho.ticketing.util.ResponseBuilder;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "/users" )
public class UserController extends BaseController
{
    private final UserService userService;

    public UserController(
            UserService userService,
            ResponseBuilder responseBuilder
    )
    {
        super( responseBuilder );

        this.userService = userService;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<UserResponseDto>> create(
            @RequestBody @Valid UserCreateDto userCreateDto
    )
    {
        UserResponseDto createdUserResponse = userService.createUser( userCreateDto );

        return createdResponse( createdUserResponse, createdUserResponse.id() );
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize( "@userSecurity.canAccessAllUsers()" )
    public ResponseEntity<ResponseDto<List<UserResponseDto>>> findAllUsers()
    {
        List<UserResponseDto> usersResponse = userService.findAll();

        return okResponse( usersResponse );
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize( "@userSecurity.canAccessUser(#id)" )
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
    @PreAuthorize( "@userSecurity.canUpdateUser(#id)" )
    public ResponseEntity<ResponseDto<UserResponseDto>> updateUser(
            @PathVariable Long id,
            @RequestBody @Valid UserUpdateDto userUpdateDto
    )
    {
        UserResponseDto updatedUserResponse = userService.updateUser( id, userUpdateDto );

        return okResponse( updatedUserResponse );
    }
}
