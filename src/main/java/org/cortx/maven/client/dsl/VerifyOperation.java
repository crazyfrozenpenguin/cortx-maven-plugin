package org.cortx.maven.client.dsl;

public interface VerifyOperation {
	VerifyCommand get(final String pathAndQuery);

	VerifyCommand post(final String pathAndQuery);

	VerifyCommand put(final String pathAndQuery);
}
