package org.cortx.maven.client;

import org.apache.http.client.fluent.Request;

public class NoBodyRetrieveCommandImpl extends RetrieveCommandImpl {

	private final String requestCommand;

	public NoBodyRetrieveCommandImpl(Request request) {
		super(request);
		this.requestCommand = request.getClass().getSimpleName();
	}

	@Override
	public String body() {
		throw new UnsupportedOperationException(this.requestCommand + " operation does not allow for body content.");
	}

}
