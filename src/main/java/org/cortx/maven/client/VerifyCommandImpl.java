package org.cortx.maven.client;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.cortx.maven.client.dsl.VerifyCommand;
import org.slf4j.Logger;

public class VerifyCommandImpl implements VerifyCommand {

	private static Logger logger = getLogger(VerifyCommandImpl.class);

	private final Request request;
	private String body;
	private Map<String, String> header = new HashMap<String, String>();

	public VerifyCommandImpl(final Request request) {
		this.request = request;
	}

	@Override
	public boolean wasCalled() {
		try {
			HttpResponse response = request.execute().returnResponse();
			return validateStatusCode(response) && validateHeaders(response) && validateBody(response);
		} catch (final Exception e) {
			logger.error("Failed to verify call: ", e);
		}
		return false;
	}

	@Override
	public VerifyCommand withBody(final String body) {
		this.body = body;
		return this;
	}

	@Override
	public VerifyCommand withHeader(final String key, final String value) {
		this.header.put(key, value);
		return this;
	}

	@Override
	public VerifyCommand withHeader(final Map<String, String> header) {
		this.header = header;
		return this;
	}
	
	private boolean validateBody(final HttpResponse response) {
		if (body == null) return true;
		try {
			final String responseBody = IOUtils.toString(response.getEntity().getContent());
			return body.equals(responseBody);
		} catch (final IllegalStateException | IOException e) {
			logger.error("Failed to read expected body: ", e);
		}
		return false;
	}

	private boolean validateHeaders(final HttpResponse response) {
		for (final Entry<String, String> entry : header.entrySet()) {
			if (response.containsHeader(entry.getKey())) {
				if (entry.getValue() != null) {
					boolean found = false;
					for (final Header header : response.getHeaders(entry.getKey())) {
						if (entry.getValue().equals(header.getValue())) {
							found = true;
							break;
						}
					}
					if (!found)	return false;
				}
			} else {
				logger.info("Failed to verify header: " + entry.getKey() + " not included." );
				return false;
			}
		}
		return true;
	}
	
	private boolean validateStatusCode(final HttpResponse response) {
		return response.getStatusLine().getStatusCode() == 200;
	}
}
