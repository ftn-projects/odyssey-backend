package com.example.odyssey.exceptions.reservations;

public class FailedCancellationException extends UnsupportedOperationException {
    public FailedCancellationException() {
        super("Reservation could not be cancelled.");
    }

    public FailedCancellationException(String reason) {
        super("Reservation could not be cancelled " + reason + ".");
    }
}
