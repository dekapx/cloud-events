package com.cloudevents.preference.controller;

import com.cloudevents.preference.domain.UserPreference;
import com.cloudevents.preference.repository.PreferenceNotFoundException;
import com.cloudevents.preference.service.PreferenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Map;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/preference")
public class PreferenceController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PreferenceController.class);

    @Inject
    private PreferenceService preferenceService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Void> save(@RequestBody final UserPreference preference) {
        LOGGER.info("Request for persisting preferences for user : {}", preference.getUsername());
        preferenceService.save(preference);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Void> update(@RequestBody final UserPreference preference) {
        LOGGER.info("Request for persisting preferences for user : {}", preference.getUsername());
        preferenceService.update(preference);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/find/{id}", method = RequestMethod.GET)
    public UserPreference find(@PathVariable final String id) {
        LOGGER.info("Request for preference received for Id: {}", id);
        return preferenceService.find(id);
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET, headers = "Accept=application/json")
    public Map<Object, UserPreference> findAll() {
        LOGGER.info("Request for retrieving all preference");
        return preferenceService.findAll();
    }

    @RequestMapping(value = "/ping", method = GET)
    public String ping() {
        LOGGER.info("--- Preference Service REST Controller ping method invoked. ---");
        return "Preference Service REST Controller...";
    }

    @ExceptionHandler(PreferenceNotFoundException.class)
    @ResponseStatus(value = NOT_FOUND, reason = "This user preference is not found in the system...")
    public void handlePreferenceNotFoundException(final Exception e) {
    }

    @ExceptionHandler(RedisConnectionFailureException.class)
    @ResponseStatus(value = INTERNAL_SERVER_ERROR, reason = "Redis server is not available. Please check the server...")
    public void handleRedisServerException(final Exception e) {
    }
}
