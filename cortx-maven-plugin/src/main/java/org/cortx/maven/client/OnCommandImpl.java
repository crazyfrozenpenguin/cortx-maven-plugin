package org.cortx.maven.client;

import static org.apache.http.entity.ContentType.DEFAULT_TEXT;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.Map;

import org.apache.http.client.fluent.Request;
import org.cortx.maven.client.dsl.OnCommand;
import org.slf4j.Logger;

public class OnCommandImpl implements OnCommand {

	private static Logger logger = getLogger(OnCommandImpl.class);

	private final Request request;

	public OnCommandImpl(final Request request) {
		this.request = request;
	}

	@Override
	public boolean response() {
		try {
			return request.execute().returnResponse().getStatusLine().getStatusCode() == 200;
		} catch (final Exception e) {
			logger.error("Failed to register return data: ", e);
		}
		return false;
	}
	
	@Override
	public OnCommand returnBody(final String body) {
		request.bodyString(body, DEFAULT_TEXT);
		return this;
	}

	@Override
	public OnCommand returnHeader(final String key, final String value) {
		request.addHeader(key, value);
		return this;
	}

	@Override
	public OnCommand returnHeaders(final Map<String, String> headers) {
		for (final String key : headers.keySet()) {
			request.addHeader(key, headers.get(key));			
		}
		return this;
	}

	@Override
	public OnCommand returnStatus(final int status) {
		request.addHeader("REGISTER_HTTP_STATUS_CODE", String.valueOf(status));
		return this;
	}

	@Override
	public OnCommand returnStatusMessage(final String statusMessage) {
		request.addHeader("REGISTER_HTTP_STATUS_MESSAGE", statusMessage);
		return this;
	}

}
