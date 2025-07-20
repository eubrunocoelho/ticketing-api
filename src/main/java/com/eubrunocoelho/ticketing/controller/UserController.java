package com.eubrunocoelho.ticketing.controller;

import com.eubrunocoelho.ticketing.dto.UserCreateDto;
import com.eubrunocoelho.ticketing.dto.UserResponseDto;
import com.eubrunocoelho.ticketing.entity.Users;
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

    private final String SCREEN_LABEL = "Ticketing API - [%s] [%s]";

    private final UserService userService;
    private final LoginUtilityService loginUtilityService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserResponseDto> create(@RequestBody @Valid UserCreateDto userDTO) {
        String label = String.format(
                SCREEN_LABEL,
                "USERS",
                ""
        );

        Users createdUser = userService.createUser(userDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();

        UserResponseDto response = new UserResponseDto(
                label,
                createdUser.getId(),
                createdUser.getUsername(),
                createdUser.getEmail()
        );

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserResponseDto> findById(@PathVariable long id) {
        Users loggedUser = loginUtilityService.getLoggedInUser();
        String label = String.format(
                SCREEN_LABEL,
                "Users",
                loggedUser.getUsername() + "/" + loggedUser.getRole().name()
        );

        Users user = userService.findById(id);

        UserResponseDto response = new UserResponseDto(
                label,
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );

        return ResponseEntity.ok().body(response);
    }
}
