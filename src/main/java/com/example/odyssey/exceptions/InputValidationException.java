package com.example.odyssey.exceptions;

import lombok.Getter;

@Getter
public class InputValidationException extends RuntimeException {
    private final String field;

    public InputValidationException(String message, String field) {
        super(message);
        this.field = field;
    }

    public InputValidationException() {
        super("Invalid field value.");
        field = null;
    }
}