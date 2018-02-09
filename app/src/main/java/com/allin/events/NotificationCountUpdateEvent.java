package com.allin.events;

/**
 * Created by harry on 12/12/17.
 */

public class NotificationCountUpdateEvent {
    private final String message;

    public NotificationCountUpdateEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
