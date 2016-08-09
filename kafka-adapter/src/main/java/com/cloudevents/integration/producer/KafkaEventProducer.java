package com.cloudevents.integration.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class KafkaEventProducer implements EventProducer, ApplicationContextAware {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaEventProducer.class);

    private MessageChannel toKafka;

    @PostConstruct
    public void init() {
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        toKafka = applicationContext.getBean("toKafka", MessageChannel.class);
        LOG.info("--- Initializing  MessageChannel {} ---", toKafka);
    }

    @Override
    public void sendToKafkaBroker(final String event) {
        LOG.info("--- event [{}] send to Kafka Broker ---", event);
        toKafka.send(new GenericMessage<>(event));
    }

}
