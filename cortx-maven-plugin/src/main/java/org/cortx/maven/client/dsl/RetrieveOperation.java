package org.cortx.maven.client.dsl;

public interface RetrieveOperation {
	RetrieveCommand get(final String pathAndQuery);

	RetrieveCommand post(final String pathAndQuery);

	RetrieveCommand put(final String pathAndQuery);
	
	RetrieveCommand delete(final String pathAndQuery);
	
	RetrieveCommand head(final String pathAndQuery);

	RetrieveCommand options(final String pathAndQuery);
	
	RetrieveCommand connect(final String pathAndQuery);
	
	RetrieveCommand trace(final String pathAndQuery);
	
	RetrieveCommand patch(final String pathAndQuery);
}
