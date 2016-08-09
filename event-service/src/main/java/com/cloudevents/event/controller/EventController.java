package com.cloudevents.event.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/event")
public class EventController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    @RequestMapping(value = "/ping", method = GET)
    public String ping() {
        LOGGER.info("--- Event Service REST Controller ping method invoked. ---");
        return "Event Service REST Controller...";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = INTERNAL_SERVER_ERROR, reason = "Event Service currently not available...")
    public void handleRedisServerException(final Exception e) {
    }
}
