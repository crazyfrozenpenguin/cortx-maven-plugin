package org.cortx.maven.client;

import org.apache.http.client.fluent.Request;
import org.cortx.maven.client.dsl.OnCommand;

public class NoBodyOnCommandImpl extends OnCommandImpl {

	private final String requestCommand;

	public NoBodyOnCommandImpl(Request request) {
		super(request);
		this.requestCommand = request.getClass().getSimpleName();
	}

	@Override
	public OnCommand returnBody(String body) {
		throw new UnsupportedOperationException(this.requestCommand + " operation does not allow for body content.");
	}

}
