package com.cloudevents.integration.controller;

import com.cloudevents.integration.service.KafkaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/event")
public class KafkaController {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaController.class);

    @Autowired
    private KafkaService kafkaService;

    @RequestMapping(value = "/toKafka", method = RequestMethod.POST)
    public ResponseEntity<Void> sendEventToKafka(@RequestBody final String eventJson) {
        LOGGER.info("--- Event [{}] received. Dispatching it to kafka service ---", eventJson);
        kafkaService.processEvent(eventJson);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


    @RequestMapping(value = "/ping", method = GET)
    public String ping() {
        LOGGER.info("--- Kafka Adapter REST Controller ping method invoked. ---");
        return "Kafka Adapter REST Controller...";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = INTERNAL_SERVER_ERROR, reason = "This request cannot be completed due to some reasons...")
    public void handlePreferenceNotFoundException(final Exception e)
    {
    }
}
