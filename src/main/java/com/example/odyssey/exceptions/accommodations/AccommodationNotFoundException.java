package com.example.odyssey.exceptions.accommodations;

import java.util.NoSuchElementException;

public class AccommodationNotFoundException extends NoSuchElementException {
    public AccommodationNotFoundException(Long id) {
        super("Accommodation with id " + id + " not found.");
    }
}
