package com.example.odyssey.exceptions.accommodationRequests;

import java.util.NoSuchElementException;

public class AccommodationRequestNotFoundException extends NoSuchElementException {
    public AccommodationRequestNotFoundException(Long id) {
        super("AccommodationRequest with id " + id + " not found.");
    }
}
