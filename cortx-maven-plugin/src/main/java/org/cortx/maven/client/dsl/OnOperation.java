package org.cortx.maven.client.dsl;

public interface OnOperation {
    OnCommand get(final String pathAndQuery);

    OnCommand post(final String pathAndQuery);

    OnCommand put(final String pathAndQuery);

    OnCommand delete(final String string);

    OnCommand head(final String string);

    OnCommand options(final String string);

    OnCommand connect(final String string);

    OnCommand trace(final String string);

    OnCommand patch(final String string);
}