package org.cortx.maven.client.dsl;

import static org.apache.http.client.fluent.Request.Get;
import static org.cortx.maven.client.CortxFactory.getCortx;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.client.fluent.Request;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

public class CortxFactoryTest {

	private static final String TEST_VALUE = "Test Value";
	private static final String CORTX_HEADER = "Cortx-Header";
	private Cortx cortx;
	
	@Before
	public void setUp() throws URISyntaxException {
		cortx = getCortx("localhost");
	}
	
	@Test
	public void shouldCreateCortxInstance() {
		// Then
		assertThat("Should create default cortx instance", cortx, CoreMatchers.notNullValue());
	}

	@Test
	public void shouldCreateCortxInstanceWithSpecificPort() throws URISyntaxException {
		// When
		cortx = getCortx("localhost", 7919);
		
		// Then
		assertThat("Should create default cortx instance", cortx, CoreMatchers.notNullValue());
	}

	@Test
	public void shouldVerifyOnGetWasCalled() throws MalformedURLException, IOException {
		// Given
		new URL("http://localhost:7919/test/url").openConnection().connect();
		
		// When/Then
		assertThat("Should verify url was called", cortx.verify().get("/test/url").wasCalled(), is(true));
	}
	
	@Test
	public void shouldVerifyOnGetWithHeaderWasCalled() throws MalformedURLException, IOException {
		// Given
		Get("http://localhost:7919/test/url").addHeader(CORTX_HEADER, TEST_VALUE).execute();
		
		// When/Then
		assertThat("Should verify url with header was called", cortx.verify().get("/test/url").withHeader(CORTX_HEADER, TEST_VALUE).wasCalled(), is(true));
	}

}
