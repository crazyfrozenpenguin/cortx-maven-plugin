package org.cortx.maven.client;

import static org.apache.http.entity.ContentType.DEFAULT_TEXT;
import static org.slf4j.LoggerFactory.getLogger;

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
	public boolean returns(final String data) {
		try {
			return request.bodyString(data, DEFAULT_TEXT).execute().returnResponse().getStatusLine().getStatusCode() == 200;
		} catch (final Exception e) {
			logger.error("Failed to register return data: ", e);
		}
		return false;
	}

}
