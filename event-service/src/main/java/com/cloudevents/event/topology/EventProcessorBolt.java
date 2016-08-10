package com.cloudevents.event.topology;

import com.cloudevents.integration.model.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class EventProcessorBolt extends BaseBasicBolt {
    private static final Logger LOG = LoggerFactory.getLogger(EventProcessorBolt.class);

	@Override
	public void execute(final Tuple tuple, final BasicOutputCollector collector) {
		final List<Object> events = tuple.getValues();
		final String contents = (String) events.iterator().next();
		try {
            final Event event = toEvent(contents);
            LOG.info("--- Event object constructed. Emitting Event to PreferenceHandlerBolt ---");
            collector.emit(new Values(event));
		} catch (IOException e) {
			LOG.error("Exception while converting the json to Event", e);
		}
	}

	@Override
	public void declareOutputFields(final OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("event"));
	}

	private Event toEvent(final String contents) throws IOException {
        LOG.info("--- Building Event object from JSON {} ---", contents);
		return new ObjectMapper().readValue(contents, Event.class);
	}
}
