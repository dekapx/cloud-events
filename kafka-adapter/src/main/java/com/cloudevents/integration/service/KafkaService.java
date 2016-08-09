package com.cloudevents.integration.service;

public interface KafkaService {
    void processEvent(String event);
}
