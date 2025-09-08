package com.eubrunocoelho.ticketing.controller.user;

import com.eubrunocoelho.ticketing.controller.AbstractController;
import com.eubrunocoelho.ticketing.dto.ResponseDto;
import com.eubrunocoelho.ticketing.dto.user.UserCreateDto;
import com.eubrunocoelho.ticketing.dto.user.UserResponseDto;
import com.eubrunocoelho.ticketing.entity.User;
import com.eubrunocoelho.ticketing.mapper.UserMapper;
import com.eubrunocoelho.ticketing.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        UserResponseDto createdUserResponse = userService.createUser(userCreateDto);

        return createdResponse(getScreenLabel(false), createdUserResponse, createdUserResponse.id());
    }

    // REFACTOR
    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<UserResponseDto>> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        UserResponseDto userResponseDto = userMapper.toDto(user);

        ResponseDto<UserResponseDto> responseDto = new ResponseDto<>(
                getScreenLabel(true),
                userResponseDto,
                null
        );

        return ResponseEntity.ok().body(responseDto);
    }
}
