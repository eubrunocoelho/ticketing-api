package com.eubrunocoelho.ticketing.controller.auth;

import com.eubrunocoelho.ticketing.controller.AbstractController;
import com.eubrunocoelho.ticketing.dto.auth.AuthDto;
import com.eubrunocoelho.ticketing.dto.auth.AuthResponseDto;
import com.eubrunocoelho.ticketing.dto.ResponseDto;
import com.eubrunocoelho.ticketing.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController extends AbstractController {

    private final AuthService authService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<AuthResponseDto>> authenticate(
            @RequestBody @Valid AuthDto authDto
    ) {
        AuthResponseDto authResponseDto = authService.authenticate(authDto);

        ResponseDto<AuthResponseDto> responseDto = new ResponseDto<>(
                getScreenLabel(false),
                authResponseDto,
                null
        );

        String authorization = "Bearer " + responseDto.data().authToken();

        return ResponseEntity
                .ok()
                .header("Authorization", authorization)
                .body(responseDto);
    }
}
