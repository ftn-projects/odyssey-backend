package com.example.odyssey.validation;

import lombok.Getter;

@Getter
public class FieldValidationException extends RuntimeException {
    private final String field;

    public FieldValidationException(String message, String field) {
        super(message);
        this.field = field;
    }

    public FieldValidationException() {
        super("Invalid field value.");
        field = null;
    }
}