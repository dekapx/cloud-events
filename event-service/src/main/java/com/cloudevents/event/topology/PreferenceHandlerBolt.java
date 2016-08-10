package com.cloudevents.event.topology;

import com.cloudevents.event.util.PreferenceMediator;
import com.cloudevents.integration.model.Event;
import com.cloudevents.preference.domain.EventTypes;
import com.cloudevents.preference.domain.UserPreference;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class PreferenceHandlerBolt extends BaseBasicBolt {
    private static final Logger LOG = LoggerFactory.getLogger(PreferenceHandlerBolt.class);

    private PreferenceMediator preferenceMediator;

    @Override
    @SuppressWarnings("rawtypes")
    public void prepare(final Map stormConf, final TopologyContext context) {
        preferenceMediator = new PreferenceMediator();
    }

    @Override
    public void execute(final Tuple tuple, final BasicOutputCollector collector) {
        final Event event = (Event) tuple.getValueByField("event");
        final String userId = String.valueOf(event.getUserId());
        LOG.info("--- Loading preferences for userId {} ---", userId);
        final UserPreference preference = preferenceMediator.findPreferenceByUserId(userId);
        LOG.info("--- Preferences found for userId {} ---", userId);

        if (preferenceMatched(event, preference)) {
            LOG.info("--- Preferences Matched for event {} ---", event.getEventType());
            collector.emit(new Values(event));
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }

    private boolean preferenceMatched(final Event event, final UserPreference preference) {
   		final EventTypes eventTypes = EventTypes.valueOf(event.getEventType());
   		return preference.getEvents().getEventTypes().contains(eventTypes);
   	}
}
