package org.cortx.maven.client;

import org.apache.http.client.fluent.Request;
import org.cortx.maven.client.dsl.VerifyCommand;

public class DeleteVerifyCommandImpl extends VerifyCommandImpl {

	public DeleteVerifyCommandImpl(final Request request) {
		super(request);
	}

	@Override
	public VerifyCommand withBody(final String body) {
		throw new UnsupportedOperationException("Delete operation does not allow for body content.");
	}

}
