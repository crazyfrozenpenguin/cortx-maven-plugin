package org.cortx.maven.client;

import org.apache.http.client.fluent.Request;
import org.cortx.maven.client.dsl.VerifyCommand;

public class HeadVerifyCommandImpl extends VerifyCommandImpl {

	public HeadVerifyCommandImpl(final Request request) {
		super(request);
	}

	@Override
	public VerifyCommand withBody(final String body) {
		throw new UnsupportedOperationException("Head operation does not allow for body content.");
	}

}
