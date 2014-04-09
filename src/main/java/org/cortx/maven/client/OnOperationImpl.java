package org.cortx.maven.client;

import static org.apache.http.client.fluent.Request.Get;
import static org.apache.http.client.fluent.Request.Post;
import static org.apache.http.client.fluent.Request.Put;

import org.cortx.maven.client.dsl.OnCommand;
import org.cortx.maven.client.dsl.OnOperation;

public class OnOperationImpl extends OperationBase implements OnOperation {

	private final StringBuffer operation;

	public OnOperationImpl(final StringBuffer operation) {
		this.operation = operation;
	}

	@Override
	public OnCommand get(final String pathAndQuery) {
		operation.append(pathAndQuery);
		return new OnCommandImpl(setDefaults(Get(operation.toString())));
	}

	@Override
	public OnCommand post(final String pathAndQuery) {
		operation.append(pathAndQuery);
		return new OnCommandImpl(Post(operation.toString()));
	}

	@Override
	public OnCommand put(final String pathAndQuery) {
		operation.append(pathAndQuery);
		return new OnCommandImpl(setDefaults(Put(operation.toString())));
	}

}
