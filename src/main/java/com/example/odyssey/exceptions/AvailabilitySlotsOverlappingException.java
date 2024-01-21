package com.example.odyssey.exceptions;

public class AvailabilitySlotsOverlappingException extends InputValidationException{
    public AvailabilitySlotsOverlappingException(String field) {super("Availability slots are overlapping.", field);}
}
