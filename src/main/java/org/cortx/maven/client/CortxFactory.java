package org.cortx.maven.client;

import java.net.URI;
import java.net.URISyntaxException;

import org.cortx.maven.client.dsl.Cortx;
import org.cortx.maven.client.dsl.OnOperation;
import org.cortx.maven.client.dsl.RetrieveOperation;
import org.cortx.maven.client.dsl.VerifyOperation;

public class CortxFactory implements Cortx {
	public static final int DEFAULT_PORT = 7919;

	private final URI cortxUrl;

	private StringBuffer operation;
	public static Cortx getCortx(final String host) throws URISyntaxException {
		return getCortx(host, null);
	}

	public static Cortx getCortx(final String host, final Integer port) throws URISyntaxException {
		return new CortxFactory(host, port == null ? DEFAULT_PORT : port);
	}

	@Override
	public OnOperation on() {
		operation = new StringBuffer(cortxUrl.toString()).append("/~");
		return new OnOperationImpl(operation);
	}

	@Override
	public VerifyOperation verify() {
		operation = new StringBuffer(cortxUrl.toString()).append("/_/");
		return new VerifyOperationImpl(operation);
	}

	@Override
	public RetrieveOperation retrieve() {
		operation = new StringBuffer(cortxUrl.toString()).append("/_/");
		return new RetrieveOperationImpl(operation);
	}

	private CortxFactory(final String host, final int port) throws URISyntaxException {
		this.cortxUrl = new URI("http://" + host + ":" + Integer.toString(port));
	}

}
