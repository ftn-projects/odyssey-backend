package com.example.odyssey.exceptions;

import lombok.Getter;

@Getter
public class FieldValidationException extends ValidationException {
    private final String field;

    public FieldValidationException(String message, String field) {
        super(message);
        this.field = field;
    }
}