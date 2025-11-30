package com.eubrunocoelho.ticketing.controller.auth;

import com.eubrunocoelho.ticketing.controller.BaseController;
import com.eubrunocoelho.ticketing.dto.auth.SignInRequestDto;
import com.eubrunocoelho.ticketing.dto.auth.AuthResponseDto;
import com.eubrunocoelho.ticketing.dto.ResponseDto;
import com.eubrunocoelho.ticketing.service.auth.AuthService;
import com.eubrunocoelho.ticketing.util.ApiResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin( "*" )
@RestController
@RequestMapping( "/auth" )
@Tag( name = "Autenticação de usuário." )
public class AuthController extends BaseController
{
    private final AuthService authService;

    public AuthController(
            AuthService authService,
            ApiResponseBuilder apiResponseBuilder
    )
    {
        super( apiResponseBuilder );

        this.authService = authService;
    }

    @Operation(
            summary = "Autenticar usuário.",
            description = "Responsável por processar as credenciais e gerar o token de autenticação de usuário."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuário autenticado.",
                            content = @Content( schema = @Schema( implementation = AuthResponseDto.class ) )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Erro de validação.",
                            content = @Content( schema = @Schema() )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Credenciais de usuário inválidas.",
                            content = @Content( schema = @Schema() )
                    )
            }
    )
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto<AuthResponseDto>> authenticate(
            @RequestBody SignInRequestDto signInRequestDto
    )
    {
        AuthResponseDto authResponse = authService.authenticate( signInRequestDto );

        return okResponse( authResponse );
    }
}
