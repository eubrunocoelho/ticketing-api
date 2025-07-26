package com.eubrunocoelho.ticketing.controller;

import com.eubrunocoelho.ticketing.dto.user.UserCreateDto;
import com.eubrunocoelho.ticketing.dto.user.UserResponseDto;
import com.eubrunocoelho.ticketing.entity.Users;
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

    private static final String SCREEN_LABEL = "Ticketing API - [%s] [%s]";

    private final UserService userService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserResponseDto> create(@RequestBody @Valid UserCreateDto userCreateDto) {
        Users user = userService.createUser(userCreateDto);

        String label = String.format(
                SCREEN_LABEL, "", ""
        );

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(
                        user.getId()
                )
                .toUri();

        UserResponseDto responseDto = new UserResponseDto(
                label,
                user.getId(),
                user.getUsername(),
                user.getRole().name()
        );

        return ResponseEntity.created(location).body(responseDto);
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserResponseDto> findById(@PathVariable long id) {
        Users user = userService.findById(id);

        String label = String.format(
                SCREEN_LABEL, "", ""
        );

        UserResponseDto responseDto = new UserResponseDto(
                label,
                user.getId(),
                user.getEmail(),
                user.getRole().name()
        );

        return ResponseEntity.ok().body(responseDto);
    }
}
