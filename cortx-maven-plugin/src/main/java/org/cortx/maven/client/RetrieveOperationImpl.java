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
        return new NoBodyRetrieveCommandImpl(setDefaults(Get(operation.toString())));
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

    @Override
    public RetrieveCommand delete(final String pathAndQuery) {
        operation.append("D").append(pathAndQuery);
        return new NoBodyRetrieveCommandImpl(setDefaults(Get(operation.toString())));
    }

    @Override
    public RetrieveCommand head(final String pathAndQuery) {
        operation.append("H").append(pathAndQuery);
        return new NoBodyRetrieveCommandImpl(setDefaults(Get(operation.toString())));
    }

    @Override
    public RetrieveCommand options(final String pathAndQuery) {
        operation.append("O").append(pathAndQuery);
        return new NoBodyRetrieveCommandImpl(setDefaults(Get(operation.toString())));
    }

    @Override
    public RetrieveCommand connect(final String pathAndQuery) {
        operation.append("C").append(pathAndQuery);
        return new RetrieveCommandImpl(setDefaults(Get(operation.toString())));
    }

    @Override
    public RetrieveCommand trace(final String pathAndQuery) {
        operation.append("T").append(pathAndQuery);
        return new NoBodyRetrieveCommandImpl(setDefaults(Get(operation.toString())));
    }

    @Override
    public RetrieveCommand patch(final String pathAndQuery) {
        operation.append("A").append(pathAndQuery);
        return new RetrieveCommandImpl(setDefaults(Get(operation.toString())));
    }

}