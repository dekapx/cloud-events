package com.cloudevents.event.topology;

import com.cloudevents.integration.model.Event;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventNotifierBolt extends BaseBasicBolt {
    private static final Logger LOG = LoggerFactory.getLogger(EventNotifierBolt.class);

	@Override
	public void execute(final Tuple tuple, final BasicOutputCollector collector) {
		final Event event = (Event) tuple.getValueByField("event");
		LOG.info("--- Event processing completed. Sending event to mobile device ---");
	}

	@Override
	public void declareOutputFields(final OutputFieldsDeclarer declarer) {
	}
}
