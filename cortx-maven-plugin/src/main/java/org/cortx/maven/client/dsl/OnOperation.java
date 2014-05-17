package org.cortx.maven.client.dsl;

public interface OnOperation {
	OnCommand get(final String pathAndQuery);

	OnCommand post(final String pathAndQuery);

	OnCommand put(final String pathAndQuery);
}
