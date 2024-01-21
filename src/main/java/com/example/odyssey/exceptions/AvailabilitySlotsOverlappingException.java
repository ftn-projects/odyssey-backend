package com.example.odyssey.exceptions;

public class AvailabilitySlotsOverlappingException extends FieldValidationException {
    public AvailabilitySlotsOverlappingException(String field) {super("Availability slots are overlapping.", field);}
}
