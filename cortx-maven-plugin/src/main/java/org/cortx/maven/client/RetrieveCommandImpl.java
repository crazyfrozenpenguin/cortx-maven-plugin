package org.cortx.maven.client;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.cortx.maven.client.dsl.RetrieveCommand;
import org.slf4j.Logger;

public class RetrieveCommandImpl implements RetrieveCommand {

    private static final String HEADER_PREFIX = "VERIFY_";

    private static Logger logger = getLogger(RetrieveCommandImpl.class);

    private HttpResponse response;

    public RetrieveCommandImpl(final Request request) {
        try {
            response = request.execute().returnResponse();
        } catch (final Exception e) {
            logger.error("Failed to retrieve: ", e);
        }
    }

    @Override
    public int statusCode() {
        if (response.containsHeader("REGISTER_HTTP_STATUS_CODE")) {
            return Integer.valueOf(response.getFirstHeader("REGISTER_HTTP_STATUS_CODE").getValue());
        }
        return 200;
    }

    @Override
    public String statusMessage() {
        if (response.containsHeader("REGISTER_HTTP_STATUS_MESSAGE")) {
            return response.getFirstHeader("REGISTER_HTTP_STATUS_MESSAGE").getValue();
        }
        return "OK";
    }

    @Override
    public String body() {
        try {
            return IOUtils.toString(response.getEntity().getContent());
        } catch (final Exception e) {
            logger.error("Failed to retrieve body: ", e);
        }
        return null;
    }

    @Override
    public Map<String, String> headers() {
        final HashMap<String, String> headers = new HashMap<>();
        for (final Header header : response.getAllHeaders()) {
            if (header.getName().startsWith(HEADER_PREFIX)) {
                headers.put(header.getName().replace(HEADER_PREFIX, ""), header.getValue());
            }
        }
        return headers;
    }

    @Override
    public String header(final String name) {
        if (response.containsHeader(HEADER_PREFIX + name)) {
            return response.getFirstHeader(HEADER_PREFIX + name).getValue();
        }
        return null;
    }

}