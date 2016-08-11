package com.cloudevents.preference.model;


public enum EventTypes {
    START_SERVER("Start Server"), SHUTDOWN_SERVER("Graceful Shutdown Server"), DEPLOY_SERVER("Deploy Server");

    private String eventType;

    private EventTypes(final String geoType) {
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }
}
