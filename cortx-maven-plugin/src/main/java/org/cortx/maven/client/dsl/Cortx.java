package org.cortx.maven.client.dsl;

public interface Cortx {
	OnOperation on();

	VerifyOperation verify();

	RetrieveOperation retrieve();
	
	boolean reset();
}
