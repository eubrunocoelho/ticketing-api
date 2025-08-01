package com.eubrunocoelho.ticketing.controller;

import com.eubrunocoelho.ticketing.dto.response.ResponseDto;
import com.eubrunocoelho.ticketing.dto.user.UserCreateDto;
import com.eubrunocoelho.ticketing.dto.user.UserResponseDto;
import com.eubrunocoelho.ticketing.entity.Users;
import com.eubrunocoelho.ticketing.mapper.UserMapper;
import com.eubrunocoelho.ticketing.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController extends AbstractController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<UserResponseDto>> create(
            @RequestBody @Valid UserCreateDto userCreateDto
    ) {
        UserResponseDto userResponseDto = userService.createUser(userCreateDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(
                        userResponseDto.id()
                )
                .toUri();

        ResponseDto<UserResponseDto> responseDto = new ResponseDto<>(
                getScreenLabel(false),
                userResponseDto
        );

        return ResponseEntity.created(location).body(responseDto);
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<UserResponseDto>> findById(@PathVariable long id) {
        Users user = userService.findById(id);
        UserResponseDto userResponseDto = userMapper.toDto(user);

        ResponseDto<UserResponseDto> responseDto = new ResponseDto<>(
                getScreenLabel(true),
                userResponseDto
        );

        return ResponseEntity.ok().body(responseDto);
    }
}
