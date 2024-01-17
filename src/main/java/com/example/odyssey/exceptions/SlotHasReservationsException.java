package com.example.odyssey.exceptions;

public class SlotHasReservationsException extends InputValidationException{
    public SlotHasReservationsException (String field) {super("Availability slot cannot be edited due to reservations made in that period.", field);}
}
