package com.eubrunocoelho.ticketing.controller.user;

import com.eubrunocoelho.ticketing.controller.BaseController;
import com.eubrunocoelho.ticketing.dto.ResponseDto;
import com.eubrunocoelho.ticketing.dto.user.UserCreateDto;
import com.eubrunocoelho.ticketing.dto.user.UserResponseDto;
import com.eubrunocoelho.ticketing.service.user.UserService;
import com.eubrunocoelho.ticketing.util.ResponseBuilder;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> findById( @PathVariable Long id )
    {
        return noContentResponse();
    }
}
