package com.cloudevents.event.util;

import com.cloudevents.preference.domain.UserPreference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PreferenceMediator {
    private static final Logger LOG = LoggerFactory.getLogger(PreferenceMediator.class);
    private static final String URI = "http://localhost:8082/preference/find/";

    public UserPreference findPreferenceByUserId(final String userId) {
        try {
            final HttpClient httpClient = HttpClientBuilder.create().build();
            final HttpGet getRequest = new HttpGet(URI + userId);
            final HttpResponse response = httpClient.execute(getRequest);
            if (response.getStatusLine().getStatusCode() != 200)
            {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
            }

            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
            final String jsonResponse = bufferedReader.readLine();
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonResponse, UserPreference.class);
        } catch (IOException e) {
            LOG.error("Unable to invoke the URL [{}] for ID {}", URI, userId, e);
            throw new RuntimeException("Unable to invoke the URL [ " + URI + " ] for ID: " + userId + ".");
        }
    }
}
