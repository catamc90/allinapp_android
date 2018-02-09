package com.allin.events;

/**
 * Created by harry on 12/12/17.
 */

public class ProfileUpdateEvent {
    private final String message;

    public ProfileUpdateEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
