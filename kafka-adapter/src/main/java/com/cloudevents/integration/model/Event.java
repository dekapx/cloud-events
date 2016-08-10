package com.cloudevents.integration.model;

public class Event {
    private String userId;
    private String eventType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventType()
    {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
