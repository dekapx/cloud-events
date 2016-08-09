package com.cloudevents.event.topology;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EventProcessorBolt extends BaseBasicBolt {
    private static final Logger LOG = LoggerFactory.getLogger(EventProcessorBolt.class);

	@Override
	public void execute(final Tuple tuple, final BasicOutputCollector collector) {
		final List<Object> events = tuple.getValues();
		final String event = (String) events.iterator().next();
        LOG.info("EventProcessorBolt - processing event [{}]", event);
	}

	@Override
	public void declareOutputFields(final OutputFieldsDeclarer declarer) {
	}
}
