package org.cortx.maven.client.dsl;

public interface RetrieveOperation {
	RetrieveCommand get(final String pathAndQuery);

	RetrieveCommand post(final String pathAndQuery);

	RetrieveCommand put(final String pathAndQuery);
}
