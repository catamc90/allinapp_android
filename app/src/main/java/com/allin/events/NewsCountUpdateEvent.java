package com.allin.events;

/**
 * Created by harry on 12/12/17.
 */

public class NewsCountUpdateEvent {
    private final String message;

    public NewsCountUpdateEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
