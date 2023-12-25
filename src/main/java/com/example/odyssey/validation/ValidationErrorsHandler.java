package com.example.odyssey.validation;

import com.example.odyssey.exceptions.InputValidationException;
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
public class ValidationErrorsHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
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

    @ExceptionHandler({InputValidationException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Map<String, String>> handleConstraintViolationException(InputValidationException e) {
        Map<String, String> errorMessages = new HashMap<>() {{
            put(e.getField(), e.getMessage());
        }};
        return new ResponseEntity<>(errorMessages, getHeaders("validation"), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({UnsupportedOperationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ResponseEntity<String> handleConstraintViolationException(UnsupportedOperationException e) {
        return new ResponseEntity<>(e.getMessage(), getHeaders("operation"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({IOException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<String> handleConstraintViolationException(IOException e) {
        return new ResponseEntity<>(e.getMessage(), getHeaders("io"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({NoSuchElementException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<String> handleConstraintViolationException(NoSuchElementException e) {
        return new ResponseEntity<>(e.getMessage(), getHeaders("database"), HttpStatus.NOT_FOUND);
    }

    private MultiValueMap<String, String> getHeaders(String errorType) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Error-Type", errorType);
        return headers;
    }
}
