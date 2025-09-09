package com.eubrunocoelho.ticketing.controller.auth;

import com.eubrunocoelho.ticketing.controller.BaseController;
import com.eubrunocoelho.ticketing.dto.auth.SigninRequestDto;
import com.eubrunocoelho.ticketing.dto.auth.AuthResponseDto;
import com.eubrunocoelho.ticketing.dto.ResponseDto;
import com.eubrunocoelho.ticketing.service.auth.AuthService;
import com.eubrunocoelho.ticketing.util.ResponseBuilder;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {

    private final AuthService authService;

    public AuthController(
            AuthService authService,
            ResponseBuilder responseBuilder
    ) {
        super(responseBuilder);

        this.authService = authService;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<AuthResponseDto>> authenticate(
            @RequestBody @Valid SigninRequestDto signinRequestDto
    ) {
        AuthResponseDto authResponse = authService.authenticate(signinRequestDto);

        return okResponse(authResponse);
    }
}
