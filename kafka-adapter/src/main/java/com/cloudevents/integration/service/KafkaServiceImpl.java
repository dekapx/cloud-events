package com.cloudevents.integration.service;

import com.cloudevents.integration.producer.EventProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class KafkaServiceImpl implements KafkaService {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaServiceImpl.class);

    @Autowired
    private EventProducer eventProducer;

    @Override
    public void processEvent(final String event) {
        LOG.info("--- event [{}] processed ---", event);
        eventProducer.sendToKafkaBroker(event);
    }
}
