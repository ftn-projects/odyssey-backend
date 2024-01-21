package com.example.odyssey.exceptions.users;

public class FailedDeactivationException extends UnsupportedOperationException {
    public FailedDeactivationException() {
        this("Account could not be deactivated.");
    }

    public FailedDeactivationException(String reason) {
        super("Account could not be deactivated " + reason + ".");
    }
}
