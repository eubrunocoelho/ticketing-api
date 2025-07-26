package com.eubrunocoelho.ticketing.controller;

import com.eubrunocoelho.ticketing.dto.UserCreateDto;
import com.eubrunocoelho.ticketing.dto.UserResponseDto;
import com.eubrunocoelho.ticketing.service.LoginUtilityService;
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
public class UserController {

    private final UserService userService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserResponseDto> create(@RequestBody @Valid UserCreateDto userCreateDto) {
        UserResponseDto responseDto = userService.createUser(userCreateDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(
                        responseDto.id()
                )
                .toUri();

        return ResponseEntity.created(location).body(responseDto);
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserResponseDto> findById(@PathVariable long id) {
        UserResponseDto responseDto = userService.findById(id);

        return ResponseEntity.ok().body(responseDto);
    }
}
