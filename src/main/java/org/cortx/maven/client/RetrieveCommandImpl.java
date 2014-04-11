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

	private final Request request;
	private HttpResponse response;

	public RetrieveCommandImpl(final Request request) {
		this.request = request;
		response = null;
	}

	@Override
	public String body() {
		try {
			response = request.execute().returnResponse();
			if (response.getStatusLine().getStatusCode() == 200) {
				// XXX I've always liked
				// EntityUtils.toString(response.getEntity());
				return IOUtils.toString(response.getEntity().getContent());
			}
		} catch (final Exception e) {
			logger.error("Failed to retrieve body: ", e);
		}
		return null;
	}

	@Override
	// Maybe refactor to "headers"
	public Map<String, String> header() {
		if (response != null) {
			final HashMap<String, String> headers = new HashMap<>();
			final Header[] headerArr = response.getAllHeaders();
			for (int i = 0; i < headerArr.length; i++) {
				headers.put(headerArr[i].getName(), headerArr[i].getValue());
			}

			return headers;
		}

		return null;
	}

	@Override
	public String headerParam(final String name) {
		// XXX I'm guessing this is what you wanted
		return response.getFirstHeader(name).getValue();
	}

}
