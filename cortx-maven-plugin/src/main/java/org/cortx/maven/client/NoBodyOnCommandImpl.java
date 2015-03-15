package org.cortx.maven.client;

import org.apache.http.client.fluent.Request;
import org.cortx.maven.client.dsl.OnCommand;

public class NoBodyOnCommandImpl extends OnCommandImpl {

    private final String requestCommand;

    public NoBodyOnCommandImpl(final Request request) {
        super(request);
        requestCommand = request.getClass().getSimpleName();
    }

    @Override
    public OnCommand returnBody(final String body) {
        throw new UnsupportedOperationException(requestCommand + " operation does not allow for body content.");
    }

}