package org.cortx.maven.client.dsl;

import static org.apache.http.client.fluent.Request.Get;
import static org.apache.http.client.fluent.Request.Post;
import static org.apache.http.client.fluent.Request.Put;
import static org.apache.http.entity.ContentType.DEFAULT_TEXT;
import static org.cortx.maven.client.CortxFactory.getCortx;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

public class CortxFactoryTest {

	private static final String CORTX_BODY = "Hello Body!";
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

	@Test(expected = UnsupportedOperationException.class)
	public void shouldNotAllowVerifyOnGetWithBody() {
		// When
		cortx.verify().get("/test/url").withBody(CORTX_BODY).wasCalled();		
	}
	
	@Test
	public void shouldVerifyOnPostWasCalled() throws MalformedURLException, IOException {
		// Given
		Post("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify POST to url was called", cortx.verify().post("/test/url").wasCalled(), is(true));
	}
	
	@Test
	public void shouldVerifyOnPostWithHeaderWasCalled() throws MalformedURLException, IOException {
		// Given
		Post("http://localhost:7919/test/url").addHeader(CORTX_HEADER, TEST_VALUE).execute();
		
		// When/Then
		assertThat("Should verify POST to url with header was called", cortx.verify().post("/test/url").withHeader(CORTX_HEADER, TEST_VALUE).wasCalled(), is(true));
	}

	@Test
	public void shouldVerifyOnPostWithBodyWasCalled() throws MalformedURLException, IOException {
		// Given
		Post("http://localhost:7919/test/url").bodyString(CORTX_BODY, DEFAULT_TEXT).execute();
		
		// When/Then
		assertThat("Should verify POST to url with header was called", cortx.verify().post("/test/url").withHeader("Content-Type", DEFAULT_TEXT.toString()).withBody(CORTX_BODY).wasCalled(), is(true));
	}

	@Test
	public void shouldVerifyOnPutWasCalled() throws MalformedURLException, IOException {
		// Given
		Put("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify PUT to url was called", cortx.verify().put("/test/url").wasCalled(), is(true));
	}
	
	@Test
	public void shouldVerifyOnPutWithHeaderWasCalled() throws MalformedURLException, IOException {
		// Given
		Put("http://localhost:7919/test/url").addHeader(CORTX_HEADER, TEST_VALUE).execute();
		
		// When/Then
		assertThat("Should verify PUT to url with header was called", cortx.verify().put("/test/url").withHeader(CORTX_HEADER, TEST_VALUE).wasCalled(), is(true));
	}

	@Test
	public void shouldVerifyOnPutWithBodyWasCalled() throws MalformedURLException, IOException {
		// Given
		Put("http://localhost:7919/test/url").bodyString(CORTX_BODY, DEFAULT_TEXT).execute();
		
		// When/Then
		assertThat("Should verify PUT to url with header was called", cortx.verify().put("/test/url").withHeader("Content-Type", DEFAULT_TEXT.toString()).withBody(CORTX_BODY).wasCalled(), is(true));
	}
}
