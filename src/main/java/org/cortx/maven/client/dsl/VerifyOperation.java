package org.cortx.maven.client.dsl;

public interface VerifyOperation {
	VerifyCommand get(final String pathAndQuery);

	VerifyCommand post(final String pathAndQuery);

	VerifyCommand put(final String pathAndQuery);
	
	VerifyCommand delete(final String pathAndQuery);
	
	VerifyCommand head(final String pathAndQuery);
	
	VerifyCommand options(final String pathAndQuery);
	
	VerifyCommand connect(final String pathAndQuery);
	
	VerifyCommand trace(final String pathAndQuery);
	
	VerifyCommand patch(final String pathAndQuery);
}
