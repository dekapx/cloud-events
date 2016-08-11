package com.cloudevents.integration.controller;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

@FunctionalInterface
public interface KafkaHttpInvoker {
    HttpResponse invoke(HttpClient httpClient, HttpPost postRequest);
}
