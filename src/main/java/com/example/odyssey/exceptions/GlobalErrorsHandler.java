package com.example.odyssey.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalErrorsHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class}) // ONLY FOR ANNOTATIONS
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Map<String, String>> handleConstraintViolationException(MethodArgumentNotValidException e) {
        Map<String, String> errorMessages = e
                .getBindingResult()
                .getAllErrors()
                .stream().collect(Collectors.toMap(
                        err -> ((FieldError) err).getField(),
                        err -> err.getDefaultMessage() != null ? err.getDefaultMessage() : ""));
        return new ResponseEntity<>(errorMessages, getHeaders("validation"), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({FieldValidationException.class}) // FOR INPUT FIELD VALIDATION
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Map<String, String>> handleConstraintViolationException(FieldValidationException e) {
        Map<String, String> errorMessages = new HashMap<>() {{
            put(e.getField(), e.getMessage());
        }};
        return new ResponseEntity<>(errorMessages, getHeaders("validation"), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({ValidationException.class}) // FOR GENERAL VALIDATION
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<String> handleConstraintViolationException(ValidationException e) {
        return new ResponseEntity<>(e.getMessage(), getHeaders("validation"), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({UnsupportedOperationException.class}) // FOR INVALID OPERATION
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<String> handleConstraintViolationException(UnsupportedOperationException e) {
        return new ResponseEntity<>(e.getMessage(), getHeaders("operation"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({IOException.class}) // FOR IO EXCEPTION
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<String> handleConstraintViolationException(IOException e) {
        return new ResponseEntity<>(e.getMessage(), getHeaders("io"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({NoSuchElementException.class}) // FOR MISSING ENTITY
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<String> handleConstraintViolationException(NoSuchElementException e) {
        return new ResponseEntity<>(e.getMessage(), getHeaders("database"), HttpStatus.NOT_FOUND);
    }

    private MultiValueMap<String, String> getHeaders(String errorType) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Error-Type", errorType);
        headers.add("Access-Control-Expose-Headers", "Error-Type");
        return headers;
    }
}
