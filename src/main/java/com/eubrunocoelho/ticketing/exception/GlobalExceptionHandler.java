package com.eubrunocoelho.ticketing.exception;

import com.eubrunocoelho.ticketing.exception.auth.InvalidCredentialsException;
import com.eubrunocoelho.ticketing.exception.business.ReplyAlreadyHasChildException;
import com.eubrunocoelho.ticketing.exception.business.TicketUpdateNotAllowedForStatusException;
import com.eubrunocoelho.ticketing.exception.business.TicketClosedReplyNotAllowedException;
import com.eubrunocoelho.ticketing.exception.business.TicketResolvedReplyNotAllowedException;
import com.eubrunocoelho.ticketing.exception.jwt.JwtTokenExpiredException;
import com.eubrunocoelho.ticketing.exception.business.SelfReplyNotAllowedException;
import com.eubrunocoelho.ticketing.exception.entity.DataBindingViolationException;
import com.eubrunocoelho.ticketing.exception.entity.ObjectNotFoundException;
import com.eubrunocoelho.ticketing.exception.validation.InvalidEnumValueException;
import com.eubrunocoelho.ticketing.exception.jwt.JwtTokenMalformedException;
import jakarta.validation.ConstraintViolation;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{
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

        exception.getBindingResult().getFieldErrors().forEach(
                fieldError -> errorResponse
                        .addValidationError( fieldError.getField(), fieldError.getDefaultMessage() )
        );

        exception.getBindingResult().getGlobalErrors().forEach(
                globalError -> errorResponse
                        .addValidationError( globalError.getObjectName(), globalError.getDefaultMessage() )
        );

        return ResponseEntity.unprocessableEntity().body( errorResponse );
    }

    @ExceptionHandler( jakarta.validation.ConstraintViolationException.class )
    @ResponseStatus( HttpStatus.UNPROCESSABLE_ENTITY )
    public ResponseEntity<Object> handleJakartaConstraintViolationException(
            jakarta.validation.ConstraintViolationException exception,
            WebRequest request
    )
    {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação. Verifique o campo \"errors\" para detalhes."
        );

        for ( ConstraintViolation<?> violation : exception.getConstraintViolations() )
        {
            String propertyPath = violation.getPropertyPath().toString();
            String fieldName = propertyPath.contains( "." )
                    ? propertyPath.substring( propertyPath.lastIndexOf( "." ) + 1 )
                    : propertyPath;

            errorResponse.addValidationError( fieldName, violation.getMessage() );
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
        final String errorMessage = "Violação de integridade de dados. Verifique entidades relacionadas.";

        return buildErrorResponse(
                exception,
                errorMessage,
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler( org.hibernate.exception.ConstraintViolationException.class )
    @ResponseStatus( HttpStatus.UNPROCESSABLE_ENTITY )
    public ResponseEntity<Object> handleHibernateConstraintViolationException(
            org.hibernate.exception.ConstraintViolationException exception,
            WebRequest request
    )
    {
        return buildErrorResponse( exception, HttpStatus.UNPROCESSABLE_ENTITY );
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

    @ExceptionHandler( JwtTokenMalformedException.class )
    @ResponseStatus( HttpStatus.UNAUTHORIZED )
    public ResponseEntity<Object> handleJwtTokenMalformedException(
            JwtTokenMalformedException exception,
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

    @ExceptionHandler( InvalidCredentialsException.class )
    @ResponseStatus( HttpStatus.UNAUTHORIZED )
    public ResponseEntity<Object> handleInvalidCredentialsException(
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

    @ExceptionHandler( AuthorizationDeniedException.class )
    @ResponseStatus( HttpStatus.FORBIDDEN )
    public ResponseEntity<Object> handleAuthorizationDeniedException(
            AuthorizationDeniedException exception,
            WebRequest request
    )
    {
        final String errorMessage = "Acesso negado. Você não tem permissão para realizar esta operação.";

        return buildErrorResponse(
                exception,
                errorMessage,
                HttpStatus.FORBIDDEN
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

    @ExceptionHandler( TicketClosedReplyNotAllowedException.class )
    @ResponseStatus( HttpStatus.FORBIDDEN )
    public ResponseEntity<Object> handleTicketClosedReplyNotAllowedException(
            TicketClosedReplyNotAllowedException exception,
            WebRequest request
    )
    {
        return buildErrorResponse(
                exception,
                exception.getMessage(),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler( TicketResolvedReplyNotAllowedException.class )
    @ResponseStatus( HttpStatus.FORBIDDEN )
    public ResponseEntity<Object> handleTicketResolvedReplyNotAllowedException(
            TicketResolvedReplyNotAllowedException exception,
            WebRequest request
    )
    {
        return buildErrorResponse(
                exception,
                exception.getMessage(),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler( TicketUpdateNotAllowedForStatusException.class )
    @ResponseStatus( HttpStatus.FORBIDDEN )
    public ResponseEntity<Object> handleTicketUpdateNotAllowedForStatusException(
            TicketUpdateNotAllowedForStatusException exception,
            WebRequest request
    )
    {
        return buildErrorResponse(
                exception,
                exception.getMessage(),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler( ReplyAlreadyHasChildException.class )
    @ResponseStatus( HttpStatus.FORBIDDEN )
    public ResponseEntity<Object> handleReplyAlreadyHasChildException(
            ReplyAlreadyHasChildException exception,
            WebRequest request
    )
    {
        return buildErrorResponse(
                exception,
                exception.getMessage(),
                HttpStatus.FORBIDDEN
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
