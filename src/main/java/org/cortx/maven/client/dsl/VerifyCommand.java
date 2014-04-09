package org.cortx.maven.client.dsl;

import java.util.Map;

public interface VerifyCommand {
	boolean wasCalled();

	VerifyCommand withBody(final String body);

	VerifyCommand withHeader(final Map<String, String> header);

	VerifyCommand withHeaderParam(final String name);
}
