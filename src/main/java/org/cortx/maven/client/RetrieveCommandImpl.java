package org.cortx.maven.client;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.cortx.maven.client.dsl.RetrieveCommand;
import org.slf4j.Logger;

public class RetrieveCommandImpl implements RetrieveCommand {

	private static Logger logger = getLogger(RetrieveCommandImpl.class);

	private final Request request;

	public RetrieveCommandImpl(final Request request) {
		this.request = request;
	}

	@Override
	public String body() {
		try {
			final HttpResponse response = request.execute().returnResponse();
			if (response.getStatusLine().getStatusCode() == 200) {
				return IOUtils.toString(response.getEntity().getContent());
			}
		} catch (final Exception e) {
			logger.error("Failed to retrieve body: ", e);
		}
		return null;
	}

	@Override
	public Map<String, String> header() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String headerParam(final String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
