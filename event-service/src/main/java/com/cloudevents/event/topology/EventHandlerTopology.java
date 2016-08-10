package com.cloudevents.event.topology;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.kafka.*;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("eventHandlerTopology")
public class EventHandlerTopology
{
    private static final Logger LOG = LoggerFactory.getLogger(EventHandlerTopology.class);

    private static final String BROKER_HOST_URL = "localhost:2181";
    private static final String KAFKA_TOPIC = "kafka-topic";
    private static final String APPLICATION_ROOT = "/event-service";
    private static final String TOPOLOGY_NAME = "EventHandlerTopology";
    private static final String ID = "some-id";

    private static final String KAFKA_SPOUT = "kafka-spout";
    private static final String EVENT_PROCESSOR_BOLT = "event-processor-bolt";
    private static final String PREFERENCE_HANDLER_BOLT = "preference-handler-bolt";

    @PostConstruct
    public void init() {
        configureTopology();
    }

    private void configureTopology() {
        LOG.info("--- eventHandlerTopology initialized ---");

        final TopologyBuilder builder = new TopologyBuilder();
        buildKafkaSpout(builder);
        buildEventProcessorBolt(builder);
        buildPreferenceHandlerBolt(builder);

        final LocalCluster cluster = deployTopologyToLocalCluster(builder);
    }

    private static LocalCluster deployTopologyToLocalCluster(final TopologyBuilder builder) {
        final Config config = new Config();
        config.setDebug(true);

        final LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(TOPOLOGY_NAME, config, builder.createTopology());
        return cluster;
    }

    private void buildKafkaSpout(final TopologyBuilder builder) {
        final BrokerHosts brokerHosts = new ZkHosts(BROKER_HOST_URL);
        final SpoutConfig spoutConfig = new SpoutConfig(brokerHosts, KAFKA_TOPIC, APPLICATION_ROOT, ID);
        spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());

        final KafkaSpout kafkaSpout = new KafkaSpout(spoutConfig);
        builder.setSpout(KAFKA_SPOUT, kafkaSpout, 1);
    }

    private void buildEventProcessorBolt(final TopologyBuilder builder) {
        builder.setBolt(EVENT_PROCESSOR_BOLT, new EventProcessorBolt()).shuffleGrouping(KAFKA_SPOUT);
    }

    private void buildPreferenceHandlerBolt(final TopologyBuilder builder) {
        builder.setBolt(PREFERENCE_HANDLER_BOLT, new PreferenceHandlerBolt()).shuffleGrouping(EVENT_PROCESSOR_BOLT);
    }

}
