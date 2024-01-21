package com.example.odyssey.exceptions.notifications;

import java.util.NoSuchElementException;

public class NotificationNotFoundException extends NoSuchElementException {
    public NotificationNotFoundException(Long id) {
        super("Notification with id " + id + " not found.");
    }
}
