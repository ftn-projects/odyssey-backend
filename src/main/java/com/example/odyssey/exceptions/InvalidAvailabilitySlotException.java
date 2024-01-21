package com.example.odyssey.exceptions;

public class InvalidAvailabilitySlotException extends InputValidationException{
    public InvalidAvailabilitySlotException(String field) {super("Invalid availability slot input.", field);}
}
