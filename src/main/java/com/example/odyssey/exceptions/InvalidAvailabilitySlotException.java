package com.example.odyssey.exceptions;

public class InvalidAvailabilitySlotException extends FieldValidationException {
    public InvalidAvailabilitySlotException(String field) {super("Invalid availability slot input.", field);}
}
