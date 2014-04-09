package org.cortx.maven.client;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.Map;

import org.apache.http.client.fluent.Request;
import org.cortx.maven.client.dsl.VerifyCommand;
import org.slf4j.Logger;

public class VerifyCommandImpl implements VerifyCommand {

	private static Logger logger = getLogger(VerifyCommandImpl.class);

	private final Request request;

	public VerifyCommandImpl(final Request request) {
		this.request = request;
	}

	@Override
	public boolean wasCalled() {
		try {
			return request.execute().returnResponse().getStatusLine().getStatusCode() == 200;
		} catch (final Exception e) {
			logger.error("Failed to verify call: ", e);
		}
		return false;
	}

	@Override
	public VerifyCommand withBody(final String body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VerifyCommand withHeader(final Map<String, String> header) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VerifyCommand withHeaderParam(final String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
