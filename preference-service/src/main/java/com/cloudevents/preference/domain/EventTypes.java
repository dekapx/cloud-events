package com.cloudevents.preference.domain;


public enum EventTypes {
    SERVER_DEPLOY("ServerDeploy");

    private String eventType;

    private EventTypes(final String geoType) {
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }
}
