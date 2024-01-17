package com.example.odyssey.exceptions.reviews;

import java.util.NoSuchElementException;

public class ReviewNotFoundException extends NoSuchElementException {
    public ReviewNotFoundException(Long id) {
        super("Review with id " + id + " not found.");
    }
}
