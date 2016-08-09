package com.cloudevents.integration.producer;

public interface EventProducer
{
    void sendToKafkaBroker(String event);
}
