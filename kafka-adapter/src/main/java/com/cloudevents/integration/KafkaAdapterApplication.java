package com.cloudevents.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.cloudevents.integration")
public class KafkaAdapterApplication extends SpringBootServletInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaAdapterApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(KafkaAdapterApplication.class, args);

//        final ConfigurableApplicationContext context
//                = new SpringApplicationBuilder(KafkaAdapterApplication.class)
//                .web(false)
//                .run(args);
//
//        MessageChannel toKafka = context.getBean("toKafka", MessageChannel.class);
//        for (int i = 0; i < 10; i++) {
//            toKafka.send(new GenericMessage<>("foo" + i));
//        }
//        PollableChannel fromKafka = context.getBean("received", PollableChannel.class);
//        Message<?> received = fromKafka.receive(10000);
//        while (received != null) {
//            received = fromKafka.receive(10000);
//            LOG.info("--- Event Received [" + received.getPayload() + "] ---");
//        }
//        context.close();
//        System.exit(0);
    }

    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder builder) {
        return builder.sources(KafkaAdapterApplication.class);
    }
}
