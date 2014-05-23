package org.cortx.maven.client;

import org.apache.http.client.fluent.Request;
import org.cortx.maven.client.dsl.VerifyCommand;

public class OptionsVerifyCommandImpl extends VerifyCommandImpl {

	public OptionsVerifyCommandImpl(final Request request) {
		super(request);
	}

	@Override
	public VerifyCommand withBody(final String body) {
		throw new UnsupportedOperationException("Options operation does not allow for body content.");
	}

}
