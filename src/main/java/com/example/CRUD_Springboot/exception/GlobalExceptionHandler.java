package com.example.CRUD_Springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.web.bind.MethodArgumentNotValidException;
import com.example.CRUD_Springboot.exception.InvalidCredentialsException;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleResourceNotFound(
            ResourceNotFoundException exception) {

        return Map.of(
                "timestamp", LocalDateTime.now(),
                "status", 404,
                "error", "NOT_FOUND",
                "message", exception.getMessage()
        );
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, Object> handleInvalidCredentials(
            InvalidCredentialsException exception) {

        return Map.of(
                "timestamp", LocalDateTime.now(),
                "status", 401,
                "error", "UNAUTHORIZED",
                "message", exception.getMessage()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationErrors(
            MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        return Map.of(
                "timestamp", LocalDateTime.now(),
                "status", 400,
                "error", "VALIDATION_FAILED",
                "message", "Validation failed",
                "errors", errors
        );
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, Object> handleAccessDenied(
            org.springframework.security.access.AccessDeniedException exception) {

        return Map.of(
                "timestamp", LocalDateTime.now(),
                "status", 403,
                "error", "FORBIDDEN",
                "message", "You do not have permission to perform this operation"
        );
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleGeneralException(Exception exception) {

        return Map.of(
                "timestamp", LocalDateTime.now(),
                "status", 500,
                "error", "INTERNAL_SERVER_ERROR",
                "message", "An unexpected error occurred"
        );
    }
}