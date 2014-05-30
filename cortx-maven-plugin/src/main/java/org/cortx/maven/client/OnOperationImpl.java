package org.cortx.maven.client;

import static org.apache.http.client.fluent.Request.Post;

import org.cortx.maven.client.dsl.OnCommand;
import org.cortx.maven.client.dsl.OnOperation;

public class OnOperationImpl extends OperationBase implements OnOperation {

	private final StringBuffer operation;

	public OnOperationImpl(final StringBuffer operation) {
		this.operation = operation;
	}

	@Override
	public OnCommand get(final String pathAndQuery) {
		return createOnCommandImpl("G/", pathAndQuery);
	}

	@Override
	public OnCommand post(final String pathAndQuery) {
		return createOnCommandImpl("P/", pathAndQuery);
	}

	@Override
	public OnCommand put(final String pathAndQuery) {
		return createOnCommandImpl("U/", pathAndQuery);
	}

	@Override
	public OnCommand delete(final String pathAndQuery) {
		return createOnCommandImpl("D/", pathAndQuery);
	}

	@Override
	public OnCommand head(final String pathAndQuery) {
		return createNoBodyOnCommandImpl("H/", pathAndQuery);
	}

	@Override
	public OnCommand options(final String pathAndQuery) {
		return createOnCommandImpl("O/", pathAndQuery);
	}

	@Override
	public OnCommand connect(final String pathAndQuery) {
		return createOnCommandImpl("C/", pathAndQuery);
	}

	@Override
	public OnCommand trace(final String pathAndQuery) {
		return createOnCommandImpl("T/", pathAndQuery);
	}

	@Override
	public OnCommand patch(final String pathAndQuery) {
		return createOnCommandImpl("A/", pathAndQuery);
	}

	private OnCommand createOnCommandImpl(final String descriminator, final String pathAndQuery) {
		final String url = operation.append(normalizeUrl(descriminator + pathAndQuery)).toString();
		return new OnCommandImpl(setDefaults(Post(url)));
	}

	private OnCommand createNoBodyOnCommandImpl(final String descriminator, final String pathAndQuery) {
		final String url = operation.append(normalizeUrl(descriminator + pathAndQuery)).toString();
		return new NoBodyOnCommandImpl(setDefaults(Post(url)));
	}
	
	private String normalizeUrl(final String url) {
		return url.replaceAll("//", "/");
	}
	
}
