package org.cortx.maven.client;

import org.apache.http.client.fluent.Request;

public abstract class OperationBase {

	protected Request setDefaults(final Request request) {
		return request.connectTimeout(1000).socketTimeout(1000);
	}

}
