package com.cloudevents.preference.controller;

import com.cloudevents.preference.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.URL;
import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PreferenceControllerIT {
    @Value("${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void savePreference() throws Exception {
        final String url = "http://localhost:" + port + "/preference/save";

        final Geo geo = new Geo();
        geo.setGeoTypes(Arrays.asList(GeoTypes.CA));
        geo.setSubscribed(true);

        final Events events = new Events();
        events.setEventTypes(Arrays.asList(EventTypes.DEPLOY_SERVER));
        events.setSubscribed(true);

        final Ownership ownership = new Ownership();
        ownership.setSubscribed(true);

        final UserPreference userPreference = new UserPreference();
        userPreference.setId("a8e6c550ca5f-2b2e-415e-a05b-003199b9");
        userPreference.setOrgId("f7af5ff251dd-99da-4623-880e-6a196ee3");
        userPreference.setUsername("TestUser");
        userPreference.setGeo(geo);
        userPreference.setEvents(events);
        userPreference.setOwnership(ownership);
        final ResponseEntity<Void> response = template.postForEntity(url, userPreference, Void.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void ping() throws Exception {
        final URL base = new URL("http://localhost:" + port + "/preference/ping");
        final ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
        assertThat(response.getBody(), equalTo("Preference Service REST Controller..."));
    }
}
