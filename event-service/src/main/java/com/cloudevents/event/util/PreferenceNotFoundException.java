package com.cloudevents.event.util;

public class PreferenceNotFoundException extends RuntimeException {

    public PreferenceNotFoundException() {
        super("User preference not found...");
    }
}
