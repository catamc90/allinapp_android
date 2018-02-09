package com.allin.events;

/**
 * Created by harry on 12/12/17.
 */

public class ImageUpdateEvent {
    private final String message;

    public ImageUpdateEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
