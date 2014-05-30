package org.cortx.maven.client;

import org.apache.http.client.fluent.Request;
import org.cortx.maven.client.dsl.VerifyCommand;

public class NoBodyVerifyCommandImpl extends VerifyCommandImpl {

	private final String requestCommand;
	
	public NoBodyVerifyCommandImpl(final Request request) {
		super(request);
		this.requestCommand = request.getClass().getSimpleName();
	}

	@Override
	public VerifyCommand withBody(final String body) {
		throw new UnsupportedOperationException(this.requestCommand + " operation does not allow for body content.");
	}

}
