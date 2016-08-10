package com.cloudevents.integration.controller;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.concurrent.TimeUnit;

public class KafkaControllerHttpInvoker {
    private static final String URI = "http://localhost:8080/event/toKafka/";

    public static void main(String[] args) throws Exception
    {
        final String contents = "{\"userId\":\"003199b9-2b2e-415e-a05b-a8e6c550ca5f\",\"eventType\":\"SERVER_DEPLOY\"}";
        final HttpClient httpClient = HttpClientBuilder.create().build();
        final HttpPost postRequest = new HttpPost(URI);
        postRequest.setEntity(new StringEntity(contents));

        for(int i = 0; i < 20; i++) {
            final HttpResponse response = httpClient.execute(postRequest);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
            }
            TimeUnit.SECONDS.sleep(1);
        }
    }

}
