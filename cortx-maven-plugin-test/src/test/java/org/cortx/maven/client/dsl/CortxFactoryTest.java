package org.cortx.maven.client.dsl;

import static org.apache.http.client.fluent.Request.Delete;
import static org.apache.http.client.fluent.Request.Get;
import static org.apache.http.client.fluent.Request.Head;
import static org.apache.http.client.fluent.Request.Options;
import static org.apache.http.client.fluent.Request.Post;
import static org.apache.http.client.fluent.Request.Put;
import static org.apache.http.client.fluent.Request.Trace;
import static org.apache.http.entity.ContentType.DEFAULT_TEXT;
import static org.cortx.maven.client.CortxFactory.getCortx;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.client.fluent.Request;
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
	
	@Test
	public void shouldVerifyOnDeleteWasCalled() throws MalformedURLException, IOException {
		// Given
		Delete("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify DELETE to url was called", cortx.verify().delete("/test/url").wasCalled(), is(true));
	}
	
	@Test
	public void shouldVerifyOnDeleteWithHeaderWasCalled() throws MalformedURLException, IOException {
		// Given
		Delete("http://localhost:7919/test/url").addHeader(CORTX_HEADER, TEST_VALUE).execute();
		
		// When/Then
		assertThat("Should verify DELETE to url with header was called", cortx.verify().delete("/test/url").withHeader(CORTX_HEADER, TEST_VALUE).wasCalled(), is(true));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void shouldNotAllowVerifyOnDeleteWithBody() {
		// When
		cortx.verify().delete("/test/url").withBody(CORTX_BODY).wasCalled();		
	}

	@Test
	public void shouldVerifyOnHeadWasCalled() throws MalformedURLException, IOException {
		// Given
		Head("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify HEAD to url was called", cortx.verify().head("/test/url").wasCalled(), is(true));
	}
	
	@Test
	public void shouldVerifyOnHeadWithHeaderWasCalled() throws MalformedURLException, IOException {
		// Given
		Head("http://localhost:7919/test/url").addHeader(CORTX_HEADER, TEST_VALUE).execute();
		
		// When/Then
		assertThat("Should verify HEAD to url with header was called", cortx.verify().head("/test/url").withHeader(CORTX_HEADER, TEST_VALUE).wasCalled(), is(true));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void shouldNotAllowVerifyOnHeadWithBody() {
		// When
		cortx.verify().head("/test/url").withBody(CORTX_BODY).wasCalled();		
	}	

	@Test
	public void shouldVerifyOnOptionsWasCalled() throws MalformedURLException, IOException {
		// Given
		Options("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify OPTIONS to url was called", cortx.verify().options("/test/url").wasCalled(), is(true));
	}

	@Test
	public void shouldVerifyOnOptionsWithHeaderWasCalled() throws MalformedURLException, IOException {
		// Given
		Options("http://localhost:7919/test/url").addHeader(CORTX_HEADER, TEST_VALUE).execute();
		
		// When/Then
		assertThat("Should verify OPTIONS to url with header was called", cortx.verify().options("/test/url").withHeader(CORTX_HEADER, TEST_VALUE).wasCalled(), is(true));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void shouldNotAllowVerifyOnOptionsWithBody() {
		// When
		cortx.verify().options("/test/url").withBody(CORTX_BODY).wasCalled();		
	}	

	@Test
	public void shouldVerifyOnTraceWasCalled() throws MalformedURLException, IOException {
		// Given
		Trace("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify TRACE to url was called", cortx.verify().trace("/test/url").wasCalled(), is(true));
	}

	@Test
	public void shouldVerifyOnTraceWithHeaderWasCalled() throws MalformedURLException, IOException {
		// Given
		Trace("http://localhost:7919/test/url").addHeader(CORTX_HEADER, TEST_VALUE).execute();
		
		// When/Then
		assertThat("Should verify TRACE to url with header was called", cortx.verify().trace("/test/url").withHeader(CORTX_HEADER, TEST_VALUE).wasCalled(), is(true));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void shouldNotAllowVerifyOnTraceWithBody() {
		// When
		cortx.verify().trace("/test/url").withBody(CORTX_BODY).wasCalled();		
	}	

}
