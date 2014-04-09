package org.cortx.maven.client.dsl;

import java.util.Map;

public interface RetrieveCommand {
	String body();

	Map<String, String> header();

	String headerParam(final String name);
}
