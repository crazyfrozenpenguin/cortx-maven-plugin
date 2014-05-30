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

	protected static Logger logger = getLogger(VerifyCommandImpl.class);

	protected final Request request;
	protected Integer statusCode;
	protected String statusMessage;
	protected String body;
	protected Map<String, String> header = new HashMap<String, String>();

	public VerifyCommandImpl(final Request request) {
		this.request = request;
	}

	@Override
	public boolean wasCalled() {
		try {
			HttpResponse response = request.execute().returnResponse();
			return validateStatusCode(response) && validateHeaders(response) && validateBody(response) && validateExpectedStatus(response);
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

	@Override
	public VerifyCommand withStatusCode(int status) {
		this.statusCode = status;
		return this;
	}

	@Override
	public VerifyCommand withStatusMessage(String message) {
		this.statusMessage = message;
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
			if (response.containsHeader("VERIFY_" + entry.getKey())) {
				if (entry.getValue() != null) {
					boolean found = false;
					for (final Header header : response.getHeaders("VERIFY_" + entry.getKey())) {
						if (entry.getValue().equals(header.getValue())) {
							found = true;
							break;
						}
					}
					if (!found)	return false;
				}
			} else {
				logger.error("Failed to verify header: " + entry.getKey() + " not included." );
				return false;
			}
		}
		return true;
	}

	private boolean validateExpectedStatus(final HttpResponse response) {
		if (statusCode != null) {
			if (response.containsHeader("REGISTER_HTTP_STATUS_CODE")) {
				return statusCode.intValue() == Integer.valueOf(response.getFirstHeader("REGISTER_HTTP_STATUS_CODE").getValue());
			} else {
				return statusCode == 200;
			}
		}
		if (statusMessage != null) {
			if (response.containsHeader("REGISTER_HTTP_STATUS_MESSAGE")) {
				return statusMessage.equals(response.getFirstHeader("REGISTER_HTTP_STATUS_MESSAGE").getValue());
			} else {
				return false;
			}
		}
		return true;
	}
	
	private boolean validateStatusCode(final HttpResponse response) {
		return response.getStatusLine().getStatusCode() == 200;
	}
}
