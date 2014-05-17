package org.cortx.maven.client.dsl;

public interface RetrieveOperation {
	RetrieveCommand get(final String pathAndQuery) throws Exception;

	RetrieveCommand post(final String pathAndQuery) throws Exception;

	RetrieveCommand put(final String pathAndQuery) throws Exception;
	
	RetrieveCommand delete(final String pathAndQuery) throws Exception;
	
	RetrieveCommand head(final String pathAndQuery) throws Exception;

	RetrieveCommand options(final String pathAndQuery) throws Exception;
	
	RetrieveCommand connect(final String pathAndQuery) throws Exception;
	
	RetrieveCommand trace(final String pathAndQuery) throws Exception;
	
	RetrieveCommand patch(final String pathAndQuery) throws Exception;
}
