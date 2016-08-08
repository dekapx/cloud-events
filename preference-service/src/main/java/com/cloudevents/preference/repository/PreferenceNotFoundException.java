package com.cloudevents.preference.repository;

public class PreferenceNotFoundException extends RuntimeException {

    public PreferenceNotFoundException() {
        super("User preference not found...");
    }
}
