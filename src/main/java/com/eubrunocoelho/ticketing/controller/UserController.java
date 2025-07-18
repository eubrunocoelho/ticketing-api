package com.eubrunocoelho.ticketing.controller;

import com.eubrunocoelho.ticketing.dto.UserCreateDTO;
import com.eubrunocoelho.ticketing.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Void> store(@RequestBody @Valid UserCreateDTO userDTO) {
        userService.createUser(userDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
