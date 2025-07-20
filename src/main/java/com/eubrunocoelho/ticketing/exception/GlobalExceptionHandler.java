package com.eubrunocoelho.ticketing.exception;

import com.eubrunocoelho.ticketing.service.exception.CredentialsInvalidException;
import com.eubrunocoelho.ticketing.service.exception.ObjectNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação. Verifique o campo \"errors\" para detalhes."
        );

        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            errorResponse.addValidationError(
                    fieldError.getField(),
                    fieldError.getDefaultMessage()
            );
        }

        return ResponseEntity.unprocessableEntity().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtException(
            Exception exception,
            WebRequest request
    ) {
        final String errorMessage = "Ocorreu um erro desconhecido.";

        return buildErrorResponse(exception, errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleDataIntegrityViolationException(
            DataIntegrityViolationException exception,
            WebRequest request
    ) {
        return buildErrorResponse(
                exception,
                exception.getMostSpecificCause().getMessage(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<Object> handleConstraintViolationException(
            ConstraintViolationException exception,
            WebRequest request
    ) {
        return buildErrorResponse(exception, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleAuthenticationException(
            AuthenticationException exception,
            WebRequest request
    ) {
        return buildErrorResponse(
                exception,
                exception.getMessage(),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(CredentialsInvalidException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleCredentialsInvalidException(
            CredentialsInvalidException exception,
            WebRequest request
    ) {
        return buildErrorResponse(
                exception,
                exception.getMessage(),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleObjectNotFoundException(
            ObjectNotFoundException exception,
            WebRequest request
    ) {
        return buildErrorResponse(
                exception,
                exception.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }

    private ResponseEntity<Object> buildErrorResponse(
            Exception exception,
            HttpStatus httpStatus
    ) {
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
    ) {
        ErrorResponse errorResponse = new ErrorResponse(
                httpStatus.value(),
                message
        );

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}
