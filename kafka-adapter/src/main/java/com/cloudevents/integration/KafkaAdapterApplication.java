package com.cloudevents.integration;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.GenericMessage;

@SpringBootApplication
public class KafkaAdapterApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        final ConfigurableApplicationContext context
                = new SpringApplicationBuilder(KafkaAdapterApplication.class)
                .web(false)
                .run(args);

        MessageChannel toKafka = context.getBean("toKafka", MessageChannel.class);
        for (int i = 0; i < 10; i++) {
            toKafka.send(new GenericMessage<>("foo" + i));
        }
        PollableChannel fromKafka = context.getBean("received", PollableChannel.class);
        Message<?> received = fromKafka.receive(10000);
        while (received != null) {
            System.out.println(received);
            received = fromKafka.receive(10000);
        }
        context.close();
        System.exit(0);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(KafkaAdapterApplication.class);
    }
}
