package org.cortx.maven.client.dsl;

import java.util.Map;

public interface OnCommand {
	
	boolean response();
	
	OnCommand returnBody(final String data);
	
	OnCommand returnHeader(final String key, final String value);
	
	OnCommand returnHeaders(final Map<String, String> headers);
}
