package org.cortx.maven.client.dsl;

import java.util.Map;

public interface VerifyCommand {
	boolean wasCalled();

	VerifyCommand withBody(final String body);

	VerifyCommand withHeader(final String key, final String value);

	VerifyCommand withHeader(final Map<String, String> header);
}
