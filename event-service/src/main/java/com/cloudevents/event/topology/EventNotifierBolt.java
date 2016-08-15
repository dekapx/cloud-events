package com.cloudevents.event.topology;

import com.cloudevents.integration.model.Event;
import org.apache.commons.io.IOUtils;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class EventNotifierBolt extends BaseBasicBolt {
    private static final Logger LOG = LoggerFactory.getLogger(EventNotifierBolt.class);
	public static final String API_KEY = "AIzaSyA_suIzZvZCWdYVxM1jre3x5mG5D1RIycI";

	@Override
	public void execute(final Tuple tuple, final BasicOutputCollector collector) {
		final Event event = (Event) tuple.getValueByField("event");
		LOG.info("--- Event processing completed. Sending event to mobile device ---");
		sendMessageToDevice();
	}

	private void sendMessageToDevice() {
		try {
			final URL url = new URL("https://gcm-http.googleapis.com/gcm/send");
			final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("Authorization", "key=" + API_KEY);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			final OutputStream outputStream = conn.getOutputStream();
			final String jsonString = "{\"data\" : {\"data\" : {\"id\" : \"c490f846-3ded-4657-a5fa-d605c5c483ca\",\"status\" : \"updated\"}},\"type\" : \"server\",\"to\" : \"et92XraD2Lg:APA91bGRvES1EnLQZTwvSd8kawcz8XQuDzYvnmYsP4tEOj1c1aLfZWWeTIVVZoBP1jrApdutz_MwGXZdT0wHYw8BeYj1BHEc2H0GmCzBDdumer_qEN63PBvjLInQ5ItFUun6xf4jr55k\"}";
			outputStream.write(jsonString.getBytes());

			// Read GCM response.
			final InputStream inputStream = conn.getInputStream();
			String response = IOUtils.toString(inputStream);
			LOG.info("--- Message sent to device... Response: {}", response);
		} catch (Exception e) {
			LOG.error("Exception while sending message to device...");
		}
	}

	@Override
	public void declareOutputFields(final OutputFieldsDeclarer declarer) {
	}
}
