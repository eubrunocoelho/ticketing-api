package com.eubrunocoelho.ticketing.exception;

import com.eubrunocoelho.ticketing.exception.auth.InvalidCredentialsException;
import com.eubrunocoelho.ticketing.security.jwt.exception.JwtTokenExpiredException;
import com.eubrunocoelho.ticketing.exception.business.SelfReplyNotAllowedException;
import com.eubrunocoelho.ticketing.exception.entity.DataBindingViolationException;
import com.eubrunocoelho.ticketing.exception.entity.ObjectNotFoundException;
import com.eubrunocoelho.ticketing.exception.validation.InvalidEnumValueException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{
    @SuppressWarnings( "unused" )
    @Value( "${server.error.include-stacktrace:NEVER}" )
    private ErrorProperties.IncludeAttribute includeStackTrace;

    @Override
    @ResponseStatus( HttpStatus.UNPROCESSABLE_ENTITY )
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,

            @NonNull
            HttpHeaders headers,

            @NonNull
            HttpStatusCode status,

            @NonNull
            WebRequest request
    )
    {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação. Verifique o campo \"errors\" para detalhes."
        );

        for ( FieldError fieldError : exception.getBindingResult().getFieldErrors() )
        {
            errorResponse.addValidationError(
                    fieldError.getField(),
                    fieldError.getDefaultMessage()
            );
        }

        return ResponseEntity.unprocessableEntity().body( errorResponse );
    }

    @ExceptionHandler( Exception.class )
    @ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR )
    public ResponseEntity<Object> handleAllUncaughtException(
            Exception exception,
            WebRequest request
    )
    {
        final String errorMessage = "Ocorreu um erro desconhecido.";

        return buildErrorResponse( exception, errorMessage, HttpStatus.INTERNAL_SERVER_ERROR );
    }

    @ExceptionHandler( DataIntegrityViolationException.class )
    @ResponseStatus( HttpStatus.CONFLICT )
    public ResponseEntity<Object> handleDataIntegrityViolationException(
            DataIntegrityViolationException exception,
            WebRequest request
    )
    {
        return buildErrorResponse(
                exception,
                exception.getMostSpecificCause().getMessage(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler( ConstraintViolationException.class )
    @ResponseStatus( HttpStatus.UNPROCESSABLE_ENTITY )
    public ResponseEntity<Object> handleConstraintViolationException(
            ConstraintViolationException exception,
            WebRequest request
    )
    {
        return buildErrorResponse( exception, HttpStatus.UNPROCESSABLE_ENTITY );
    }

    @ExceptionHandler( AuthenticationException.class )
    @ResponseStatus( HttpStatus.UNAUTHORIZED )
    public ResponseEntity<Object> handleAuthenticationException(
            AuthenticationException exception,
            WebRequest request
    )
    {
        return buildErrorResponse(
                exception,
                exception.getMessage(),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler( ObjectNotFoundException.class )
    @ResponseStatus( HttpStatus.NOT_FOUND )
    public ResponseEntity<Object> handleObjectNotFoundException(
            ObjectNotFoundException exception,
            WebRequest request
    )
    {
        return buildErrorResponse(
                exception,
                exception.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler( DataBindingViolationException.class )
    @ResponseStatus( HttpStatus.CONFLICT )
    public ResponseEntity<Object> handleDataBindingViolationException(
            DataBindingViolationException exception,
            WebRequest request
    )
    {
        return buildErrorResponse(
                exception,
                exception.getMessage(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler( InvalidEnumValueException.class )
    @ResponseStatus( HttpStatus.UNPROCESSABLE_ENTITY )
    public ResponseEntity<Object> handleInvalidEnumValueException(
            InvalidEnumValueException exception,
            WebRequest request
    )
    {
        return buildErrorResponse(
                exception,
                exception.getMessage(),
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }

    @ExceptionHandler( InvalidCredentialsException.class )
    @ResponseStatus( HttpStatus.UNAUTHORIZED )
    public ResponseEntity<Object> handleCredentialsInvalidException(
            InvalidCredentialsException exception,
            WebRequest request
    )
    {
        return buildErrorResponse(
                exception,
                exception.getMessage(),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler( JwtTokenExpiredException.class )
    @ResponseStatus( HttpStatus.UNAUTHORIZED )
    public ResponseEntity<Object> handleJwtTokenExpiredException(
            JwtTokenExpiredException exception,
            WebRequest request
    )
    {
        return buildErrorResponse(
                exception,
                exception.getMessage(),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler( SelfReplyNotAllowedException.class )
    @ResponseStatus( HttpStatus.FORBIDDEN )
    public ResponseEntity<Object> handleSelfReplyNotAllowedException(
            SelfReplyNotAllowedException exception,
            WebRequest request
    )
    {
        return buildErrorResponse(
                exception,
                exception.getMessage(),
                HttpStatus.FORBIDDEN
        );
    }

    // Inspect
    private ResponseEntity<Object> buildErrorResponse(
            Exception exception,
            HttpStatus httpStatus
    )
    {
        return buildErrorResponse(
                exception,
                exception.getMessage(),
                httpStatus
        );
    }

    private ResponseEntity<Object> buildErrorResponse(
            Exception exception,
            String message,
            HttpStatus httpStatus
    )
    {
        ErrorResponse errorResponse = new ErrorResponse(
                httpStatus.value(),
                message
        );

        if ( includeStackTrace == ErrorProperties.IncludeAttribute.ALWAYS )
        {
            errorResponse.setStackTrace( ExceptionUtils.getStackTrace( exception ) );
        }

        return ResponseEntity.status( httpStatus ).body( errorResponse );
    }
}
