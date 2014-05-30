package org.cortx.maven.client.dsl;

import java.util.Map;

public interface RetrieveCommand {
	int statusCode();

	String statusMessage();
	
	String body();

	Map<String, String> headers();

	String header(final String name);
}
