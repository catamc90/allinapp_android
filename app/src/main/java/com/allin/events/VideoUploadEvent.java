package com.allin.events;

/**
 * Created by harry on 12/14/17.
 */

public class VideoUploadEvent {
    private final String message;

    public VideoUploadEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
