package com.cloudevents.preference.util;

import com.cloudevents.preference.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;

public class JsonUtil {
    public static <T> String objectToJson(T object) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    public static void main(String[] args) throws Exception {
        final Geo geo = new Geo();
        geo.setGeoTypes(Arrays.asList(GeoTypes.CA));
        geo.setSubscribed(true);

        final Events events = new Events();
        events.setEventTypes(Arrays.asList(EventTypes.START_SERVER, EventTypes.SHUTDOWN_SERVER, EventTypes.DEPLOY_SERVER));
        events.setSubscribed(true);

        final Ownership ownership = new Ownership();
        ownership.setSubscribed(true);

        final UserPreference userPreference = new UserPreference();
        userPreference.setId("003199b9-2b2e-415e-a05b-a8e6c550ca5f");
        userPreference.setOrgId("6a196ee3-99da-4623-880e-f7af5ff251dd");
        userPreference.setUsername("DemoUser");
        userPreference.setGeo(geo);
        userPreference.setEvents(events);
        userPreference.setOwnership(ownership);

        final String jsonString = objectToJson(userPreference);
        System.out.println(jsonString);
    }

}
