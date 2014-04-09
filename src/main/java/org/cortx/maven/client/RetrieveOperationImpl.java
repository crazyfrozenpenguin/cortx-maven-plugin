package org.cortx.maven.client;

import static org.apache.http.client.fluent.Request.Get;

import org.cortx.maven.client.dsl.RetrieveCommand;
import org.cortx.maven.client.dsl.RetrieveOperation;

public class RetrieveOperationImpl extends OperationBase implements RetrieveOperation {

	private final StringBuffer operation;

	public RetrieveOperationImpl(final StringBuffer operation) {
		this.operation = operation;
	}

	@Override
	public RetrieveCommand get(final String pathAndQuery) {
		operation.append("G").append(pathAndQuery);
		return new RetrieveCommandImpl(setDefaults(Get(operation.toString())));
	}

	@Override
	public RetrieveCommand post(final String pathAndQuery) {
		operation.append("P").append(pathAndQuery);
		return new RetrieveCommandImpl(setDefaults(Get(operation.toString())));
	}

	@Override
	public RetrieveCommand put(final String pathAndQuery) {
		operation.append("U").append(pathAndQuery);
		return new RetrieveCommandImpl(setDefaults(Get(operation.toString())));
	}

}
