package org.cortx.maven.client;

import static org.apache.http.client.fluent.Request.Get;

import org.cortx.maven.client.dsl.VerifyCommand;
import org.cortx.maven.client.dsl.VerifyOperation;

public class VerifyOperationImpl extends OperationBase implements VerifyOperation {

	private final StringBuffer operation;

	public VerifyOperationImpl(final StringBuffer operation) {
		this.operation = operation;
	}

	@Override
	public VerifyCommand get(final String pathAndQuery) {
		operation.append("G").append(pathAndQuery);
		return new GetVerifyCommandImpl(setDefaults(Get(operation.toString())));
	}

	@Override
	public VerifyCommand post(final String pathAndQuery) {
		operation.append("P").append(pathAndQuery);
		return new VerifyCommandImpl(setDefaults(Get(operation.toString())));
	}

	@Override
	public VerifyCommand put(final String pathAndQuery) {
		operation.append("U").append(pathAndQuery);
		return new VerifyCommandImpl(setDefaults(Get(operation.toString())));
	}

	@Override
	public VerifyCommand delete(final String pathAndQuery) {
		operation.append("D").append(pathAndQuery);
		return new DeleteVerifyCommandImpl(setDefaults(Get(operation.toString())));
	}

	@Override
	public VerifyCommand head(final String pathAndQuery) {
		operation.append("H").append(pathAndQuery);
		return new HeadVerifyCommandImpl(setDefaults(Get(operation.toString())));
	}

	@Override
	public VerifyCommand options(final String pathAndQuery) {
		operation.append("O").append(pathAndQuery);
		return new OptionsVerifyCommandImpl(setDefaults(Get(operation.toString())));
	}

	@Override
	public VerifyCommand connect(final String pathAndQuery) {
		operation.append("C").append(pathAndQuery);
		return new VerifyCommandImpl(setDefaults(Get(operation.toString())));
	}

	@Override
	public VerifyCommand trace(final String pathAndQuery) {
		operation.append("T").append(pathAndQuery);
		return new VerifyCommandImpl(setDefaults(Get(operation.toString())));
	}

	@Override
	public VerifyCommand patch(final String pathAndQuery) {
		operation.append("A").append(pathAndQuery);
		return new VerifyCommandImpl(setDefaults(Get(operation.toString())));
	}
}
