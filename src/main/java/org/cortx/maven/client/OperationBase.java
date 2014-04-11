package org.cortx.maven.client;

import org.apache.http.client.fluent.Request;

public abstract class OperationBase {
	public static final int DEFAULT_CONNECT_TIMEOUT = 1000;
	public static final int DEFAULT_SOCKET_TIMEOUT = 1000;

	protected Request setDefaults(final Request request) {
		return request.connectTimeout(DEFAULT_SOCKET_TIMEOUT).socketTimeout(
				DEFAULT_SOCKET_TIMEOUT);
	}

}
