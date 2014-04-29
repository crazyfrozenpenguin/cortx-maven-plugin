package org.cortx.maven.client;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.cortx.maven.client.dsl.RetrieveCommand;
import org.slf4j.Logger;

public class RetrieveCommandImpl implements RetrieveCommand {

	private static Logger logger = getLogger(RetrieveCommandImpl.class);

	private HttpResponse response;

	public RetrieveCommandImpl(final Request request) throws Exception {
		response = request.execute().returnResponse();
	}

	@Override
	public int statusCode() {
		return response.getStatusLine().getStatusCode();
	}

	@Override
	public String body() {
		try {
			return IOUtils.toString(response.getEntity().getContent());
		} catch (final Exception e) {
			logger.error("Failed to retrieve body: ", e);
		}
		return null;
	}

	@Override
	public Map<String, String> headers() {
		if (response == null) return null;
		
		final HashMap<String, String> headers = new HashMap<>();
		for (final Header header : response.getAllHeaders()) {
			headers.put(header.getName(), header.getValue());
		}
		return headers;
	}

	@Override
	public String header(final String name) {
		return response == null ? null : response.getFirstHeader(name).getValue();
	}

}
