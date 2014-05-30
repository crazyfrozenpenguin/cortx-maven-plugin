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
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Response;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

public class CortxFactoryTest {

	private static final int STATUS_204 = 204;
	private static final String CORTX_BODY = "Hello Body!";
	private static final String TEST_VALUE = "Test Value";
	private static final String CORTX_HEADER = "Cortx-Header";
	private Cortx cortx;
	
	@Before
	public void setUp() throws URISyntaxException {
		cortx = getCortx("localhost");
		
		cortx.reset();
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
		Get("http://localhost:7919/test/url").execute();
		
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
	public void shouldVerifyOnGetWithStatusCodeWasCalled() throws MalformedURLException, IOException {
		// Given
		cortx.on().get("/test/url").returnStatus(STATUS_204).response();
		Get("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify url got called with expected status code", cortx.verify().get("/test/url").withStatusCode(STATUS_204).wasCalled(), is(true));
	}

	@Test
	public void shouldVerifyOnGetWithDefaultStatusCodeWasCalled() throws MalformedURLException, IOException {
		// Given
		Get("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify url got called with expected default status code", cortx.verify().get("/test/url").wasCalled(), is(true));
	}

	@Test
	public void shouldVerifyOnGetWithStatusMessageWasCalled() throws MalformedURLException, IOException {
		// Given
		cortx.on().get("/test/url").returnStatusMessage(TEST_VALUE).response();
		Get("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify url got called with expected status message", cortx.verify().get("/test/url").withStatusMessage(TEST_VALUE).wasCalled(), is(true));
	}

	@Test
	public void shouldVerifyOnGetWithDefaultStatusMessageWasCalled() throws MalformedURLException, IOException {
		// Given
		cortx.on().get("/test/url").response();
		Get("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify url got called with default status message", cortx.verify().get("/test/url").wasCalled(), is(true));
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
	public void shouldVerifyOnPostWithStatusCodeWasCalled() throws MalformedURLException, IOException {
		// Given
		cortx.on().post("/test/url").returnStatus(STATUS_204).response();
		Post("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify url got called with expected status code", cortx.verify().post("/test/url").withStatusCode(STATUS_204).wasCalled(), is(true));
	}

	@Test
	public void shouldVerifyOnPostWithDefaultStatusCodeWasCalled() throws MalformedURLException, IOException {
		// Given
		Post("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify url got called with expected default status code", cortx.verify().post("/test/url").wasCalled(), is(true));
	}

	@Test
	public void shouldVerifyOnPostWithStatusMessageWasCalled() throws MalformedURLException, IOException {
		// Given
		cortx.on().post("/test/url").returnStatusMessage(TEST_VALUE).response();
		Post("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify url got called with expected status message", cortx.verify().post("/test/url").withStatusMessage(TEST_VALUE).wasCalled(), is(true));
	}

	@Test
	public void shouldVerifyOnPostWithDefaultStatusMessageWasCalled() throws MalformedURLException, IOException {
		// Given
		cortx.on().post("/test/url").response();
		Post("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify url got called with default status message", cortx.verify().post("/test/url").wasCalled(), is(true));
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
	public void shouldVerifyOnPutWithStatusCodeWasCalled() throws MalformedURLException, IOException {
		// Given
		cortx.on().put("/test/url").returnStatus(STATUS_204).response();
		Put("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify url got called with expected status code", cortx.verify().put("/test/url").withStatusCode(STATUS_204).wasCalled(), is(true));
	}

	@Test
	public void shouldVerifyOnPutWithDefaultStatusCodeWasCalled() throws MalformedURLException, IOException {
		// Given
		Put("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify url got called with expected default status code", cortx.verify().put("/test/url").wasCalled(), is(true));
	}

	@Test
	public void shouldVerifyOnPutWithStatusMessageWasCalled() throws MalformedURLException, IOException {
		// Given
		cortx.on().put("/test/url").returnStatusMessage(TEST_VALUE).response();
		Put("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify url got called with expected status message", cortx.verify().put("/test/url").withStatusMessage(TEST_VALUE).wasCalled(), is(true));
	}

	@Test
	public void shouldVerifyOnPutWithDefaultStatusMessageWasCalled() throws MalformedURLException, IOException {
		// Given
		cortx.on().put("/test/url").response();
		Put("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify url got called with default status message", cortx.verify().put("/test/url").wasCalled(), is(true));
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
	public void shouldVerifyOnDeleteWithStatusCodeWasCalled() throws MalformedURLException, IOException {
		// Given
		cortx.on().delete("/test/url").returnStatus(STATUS_204).response();
		Delete("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify url got called with expected status code", cortx.verify().delete("/test/url").withStatusCode(STATUS_204).wasCalled(), is(true));
	}

	@Test
	public void shouldVerifyOnDeleteWithDefaultStatusCodeWasCalled() throws MalformedURLException, IOException {
		// Given
		Delete("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify url got called with expected default status code", cortx.verify().delete("/test/url").wasCalled(), is(true));
	}

	@Test
	public void shouldVerifyOnDeleteWithStatusMessageWasCalled() throws MalformedURLException, IOException {
		// Given
		cortx.on().delete("/test/url").returnStatusMessage(TEST_VALUE).response();
		Delete("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify url got called with expected status message", cortx.verify().delete("/test/url").withStatusMessage(TEST_VALUE).wasCalled(), is(true));
	}

	@Test
	public void shouldVerifyOnDeleteWithDefaultStatusMessageWasCalled() throws MalformedURLException, IOException {
		// Given
		cortx.on().delete("/test/url").response();
		Delete("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify url got called with default status message", cortx.verify().delete("/test/url").wasCalled(), is(true));
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
	public void shouldVerifyOnHeadWithStatusCodeWasCalled() throws MalformedURLException, IOException {
		// Given
		cortx.on().head("/test/url").returnStatus(STATUS_204).response();
		Head("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify url got called with expected status code", cortx.verify().head("/test/url").withStatusCode(STATUS_204).wasCalled(), is(true));
	}

	@Test
	public void shouldVerifyOnHeadWithDefaultStatusCodeWasCalled() throws MalformedURLException, IOException {
		// Given
		Head("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify url got called with expected default status code", cortx.verify().head("/test/url").wasCalled(), is(true));
	}

	@Test
	public void shouldVerifyOnHeadWithStatusMessageWasCalled() throws MalformedURLException, IOException {
		// Given
		cortx.on().head("/test/url").returnStatusMessage(TEST_VALUE).response();
		Head("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify url got called with expected status message", cortx.verify().head("/test/url").withStatusMessage(TEST_VALUE).wasCalled(), is(true));
	}

	@Test
	public void shouldVerifyOnHeadWithDefaultStatusMessageWasCalled() throws MalformedURLException, IOException {
		// Given
		cortx.on().head("/test/url").response();
		Head("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify url got called with default status message", cortx.verify().head("/test/url").wasCalled(), is(true));
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
	public void shouldVerifyOnOptionsWithStatusCodeWasCalled() throws MalformedURLException, IOException {
		// Given
		cortx.on().options("/test/url").returnStatus(STATUS_204).response();
		Options("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify url got called with expected status code", cortx.verify().options("/test/url").withStatusCode(STATUS_204).wasCalled(), is(true));
	}

	@Test
	public void shouldVerifyOnOptionsWithDefaultStatusCodeWasCalled() throws MalformedURLException, IOException {
		// Given
		Options("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify url got called with expected default status code", cortx.verify().options("/test/url").wasCalled(), is(true));
	}

	@Test
	public void shouldVerifyOnOptionsWithStatusMessageWasCalled() throws MalformedURLException, IOException {
		// Given
		cortx.on().options("/test/url").returnStatusMessage(TEST_VALUE).response();
		Options("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify url got called with expected status message", cortx.verify().options("/test/url").withStatusMessage(TEST_VALUE).wasCalled(), is(true));
	}

	@Test
	public void shouldVerifyOnOptionsWithDefaultStatusMessageWasCalled() throws MalformedURLException, IOException {
		// Given
		cortx.on().options("/test/url").response();
		Options("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify url got called with default status message", cortx.verify().options("/test/url").wasCalled(), is(true));
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
		
	@Test
	public void shouldVerifyOnTraceWithStatusCodeWasCalled() throws MalformedURLException, IOException {
		// Given
		cortx.on().trace("/test/url").returnStatus(STATUS_204).response();
		Trace("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify url got called with expected status code", cortx.verify().trace("/test/url").withStatusCode(STATUS_204).wasCalled(), is(true));
	}

	@Test
	public void shouldVerifyOnTraceWithDefaultStatusCodeWasCalled() throws MalformedURLException, IOException {
		// Given
		Trace("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify url got called with expected default status code", cortx.verify().trace("/test/url").wasCalled(), is(true));
	}

	@Test
	public void shouldVerifyOnTraceWithStatusMessageWasCalled() throws MalformedURLException, IOException {
		// Given
		cortx.on().trace("/test/url").returnStatusMessage(TEST_VALUE).response();
		Trace("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify url got called with expected status message", cortx.verify().trace("/test/url").withStatusMessage(TEST_VALUE).wasCalled(), is(true));
	}

	@Test
	public void shouldVerifyOnTraceWithDefaultStatusMessageWasCalled() throws MalformedURLException, IOException {
		// Given
		cortx.on().trace("/test/url").response();
		Trace("http://localhost:7919/test/url").execute();
		
		// When/Then
		assertThat("Should verify url got called with default status message", cortx.verify().trace("/test/url").wasCalled(), is(true));
	}

	@Test
	public void shouldResetServer() throws ClientProtocolException, IOException {
		// Given
		Get("http://localhost:7919/test/url").execute();
		
		// When
		cortx.reset();
		
		// Then
		assertThat("Should reset CortX server", cortx.verify().get("/test/url").wasCalled(), is(false));
	}
	
	@Test
	public void shouldRetrieveGetStatusCode() throws ClientProtocolException, IOException {
		// Given
		Get("http://localhost:7919/test/url").execute();
		
		// When
		final int status = cortx.retrieve().get("/test/url").statusCode();
		
		// Then
		assertThat(status, is(200));
	}
	
	@Test
	public void shouldRetrieveSpecifiedGetStatusCode() throws ClientProtocolException, IOException {
		// Given
		cortx.on().get("/test/url").returnStatus(STATUS_204).response();
		Get("http://localhost:7919/test/url").execute();
		
		// When
		final int status = cortx.retrieve().get("/test/url").statusCode();
		
		// Then
		assertThat(status, is(STATUS_204));
	}
	
	@Test
	public void shouldRetrieveGetStatusMessage() throws ClientProtocolException, IOException {
		// Given
		Get("http://localhost:7919/test/url").execute();
		
		// When
		final String message = cortx.retrieve().get("/test/url").statusMessage();
		
		// Then
		assertThat(message, is("OK"));
	}
	
	@Test
	public void shouldRetrieveSpecifiedGetStatusMessage() throws ClientProtocolException, IOException {
		// Given
		cortx.on().get("/test/url").returnStatusMessage(TEST_VALUE).response();
		Get("http://localhost:7919/test/url").execute();
		
		// When
		final String message = cortx.retrieve().get("/test/url").statusMessage();
		
		// Then
		assertThat(message, is(TEST_VALUE));
	}
	
	@Test
	public void shouldRetrieveGetHeader() throws ClientProtocolException, IOException {
		// Given
		Get("http://localhost:7919/test/url").addHeader(CORTX_HEADER, TEST_VALUE).execute();
		
		// When
		final String header = cortx.retrieve().get("/test/url").header(CORTX_HEADER);
		
		// Then
		assertThat(header, is(TEST_VALUE));
	}

	@Test
	public void shouldRetrieveGetHeaders() throws ClientProtocolException, IOException {
		// Given
		Get("http://localhost:7919/test/url").addHeader(CORTX_HEADER, TEST_VALUE).execute();
		
		// When
		final Map<String, String> headers = cortx.retrieve().get("/test/url").headers();
		
		// Then
		assertThat(headers.containsKey(CORTX_HEADER), is(true));
		assertThat(headers.get(CORTX_HEADER), is(TEST_VALUE));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void shouldNotAllowToRetrieveGetBody() throws ClientProtocolException, IOException {
		// Given/When/Then
		cortx.retrieve().get("/test/url").body();
	}
	
	@Test
	public void shouldRetrievePostStatusCode() throws ClientProtocolException, IOException {
		// Given
		Post("http://localhost:7919/test/url").execute();
		
		// When
		final int status = cortx.retrieve().post("/test/url").statusCode();
		
		// Then
		assertThat(status, is(200));
	}

	@Test
	public void shouldRetrieveSpecifiedPostStatusCode() throws ClientProtocolException, IOException {
		// Given
		cortx.on().post("/test/url").returnStatus(STATUS_204).response();
		Post("http://localhost:7919/test/url").execute();
		
		// When
		final int status = cortx.retrieve().post("/test/url").statusCode();
		
		// Then
		assertThat(status, is(STATUS_204));
	}
	
	@Test
	public void shouldRetrievePostStatusMessage() throws ClientProtocolException, IOException {
		// Given
		Post("http://localhost:7919/test/url").execute();
		
		// When
		final String message = cortx.retrieve().post("/test/url").statusMessage();
		
		// Then
		assertThat(message, is("OK"));
	}
	
	@Test
	public void shouldRetrieveSpecifiedPostStatusMessage() throws ClientProtocolException, IOException {
		// Given
		cortx.on().post("/test/url").returnStatusMessage(TEST_VALUE).response();
		Post("http://localhost:7919/test/url").execute();
		
		// When
		final String message = cortx.retrieve().post("/test/url").statusMessage();
		
		// Then
		assertThat(message, is(TEST_VALUE));
	}
	
	@Test
	public void shouldRetrievePostHeader() throws ClientProtocolException, IOException {
		// Given
		Post("http://localhost:7919/test/url").addHeader(CORTX_HEADER, TEST_VALUE).execute();
		
		// When
		final String header = cortx.retrieve().post("/test/url").header(CORTX_HEADER);
		
		// Then
		assertThat(header, is(TEST_VALUE));
	}

	@Test
	public void shouldRetrievePostHeaders() throws ClientProtocolException, IOException {
		// Given
		Post("http://localhost:7919/test/url").addHeader(CORTX_HEADER, TEST_VALUE).execute();
		
		// When
		final Map<String, String> headers = cortx.retrieve().post("/test/url").headers();
		
		// Then
		assertThat(headers.containsKey(CORTX_HEADER), is(true));
		assertThat(headers.get(CORTX_HEADER), is(TEST_VALUE));
	}
	
	@Test
	public void shouldRetrievePostBody() throws ClientProtocolException, IOException {
		// Given
		Post("http://localhost:7919/test/url").bodyString(CORTX_BODY, DEFAULT_TEXT).execute();
		
		// When
		final String body = cortx.retrieve().post("/test/url").body();
		
		// Then
		assertThat(body, is(CORTX_BODY));
	}
	
	@Test
	public void shouldRetrievePutStatusCode() throws ClientProtocolException, IOException {
		// Given
		Put("http://localhost:7919/test/url").execute();
		
		// When
		final int status = cortx.retrieve().put("/test/url").statusCode();
		
		// Then
		assertThat(status, is(200));
	}

	@Test
	public void shouldRetrieveSpecifiedPutStatusCode() throws ClientProtocolException, IOException {
		// Given
		cortx.on().put("/test/url").returnStatus(STATUS_204).response();
		Put("http://localhost:7919/test/url").execute();
		
		// When
		final int status = cortx.retrieve().put("/test/url").statusCode();
		
		// Then
		assertThat(status, is(STATUS_204));
	}
	
	@Test
	public void shouldRetrievePutStatusMessage() throws ClientProtocolException, IOException {
		// Given
		Put("http://localhost:7919/test/url").execute();
		
		// When
		final String message = cortx.retrieve().put("/test/url").statusMessage();
		
		// Then
		assertThat(message, is("OK"));
	}
	
	@Test
	public void shouldRetrieveSpecifiedPutStatusMessage() throws ClientProtocolException, IOException {
		// Given
		cortx.on().put("/test/url").returnStatusMessage(TEST_VALUE).response();
		Put("http://localhost:7919/test/url").execute();
		
		// When
		final String message = cortx.retrieve().put("/test/url").statusMessage();
		
		// Then
		assertThat(message, is(TEST_VALUE));
	}
	
	@Test
	public void shouldRetrievePutHeader() throws ClientProtocolException, IOException {
		// Given
		Put("http://localhost:7919/test/url").addHeader(CORTX_HEADER, TEST_VALUE).execute();
		
		// When
		final String header = cortx.retrieve().put("/test/url").header(CORTX_HEADER);
		
		// Then
		assertThat(header, is(TEST_VALUE));
	}

	@Test
	public void shouldRetrievePutHeaders() throws ClientProtocolException, IOException {
		// Given
		Put("http://localhost:7919/test/url").addHeader(CORTX_HEADER, TEST_VALUE).execute();
		
		// When
		final Map<String, String> headers = cortx.retrieve().put("/test/url").headers();
		
		// Then
		assertThat(headers.containsKey(CORTX_HEADER), is(true));
		assertThat(headers.get(CORTX_HEADER), is(TEST_VALUE));
	}
	
	@Test
	public void shouldRetrievePutBody() throws ClientProtocolException, IOException {
		// Given
		Put("http://localhost:7919/test/url").bodyString(CORTX_BODY, DEFAULT_TEXT).execute();
		
		// When
		final String body = cortx.retrieve().put("/test/url").body();
		
		// Then
		assertThat(body, is(CORTX_BODY));
	}
	
	@Test
	public void shouldRetrieveDeleteStatusCode() throws ClientProtocolException, IOException {
		// Given
		Delete("http://localhost:7919/test/url").execute();
		
		// When
		final int status = cortx.retrieve().delete("/test/url").statusCode();
		
		// Then
		assertThat(status, is(200));
	}
	
	@Test
	public void shouldRetrieveSpecifiedDeleteStatusCode() throws ClientProtocolException, IOException {
		// Given
		cortx.on().delete("/test/url").returnStatus(STATUS_204).response();
		Delete("http://localhost:7919/test/url").execute();
		
		// When
		final int status = cortx.retrieve().delete("/test/url").statusCode();
		
		// Then
		assertThat(status, is(STATUS_204));
	}
	
	@Test
	public void shouldRetrieveDeleteStatusMessage() throws ClientProtocolException, IOException {
		// Given
		Delete("http://localhost:7919/test/url").execute();
		
		// When
		final String message = cortx.retrieve().delete("/test/url").statusMessage();
		
		// Then
		assertThat(message, is("OK"));
	}

	@Test
	public void shouldRetrieveSpecifiedDeleteStatusMessage() throws ClientProtocolException, IOException {
		// Given
		cortx.on().delete("/test/url").returnStatusMessage(TEST_VALUE).response();
		Delete("http://localhost:7919/test/url").execute();
		
		// When
		final String message = cortx.retrieve().delete("/test/url").statusMessage();
		
		// Then
		assertThat(message, is(TEST_VALUE));
	}

	@Test
	public void shouldRetrieveDeleteHeader() throws ClientProtocolException, IOException {
		// Given
		Delete("http://localhost:7919/test/url").addHeader(CORTX_HEADER, TEST_VALUE).execute();
		
		// When
		final String header = cortx.retrieve().delete("/test/url").header(CORTX_HEADER);
		
		// Then
		assertThat(header, is(TEST_VALUE));
	}

	@Test
	public void shouldRetrieveDeleteHeaders() throws ClientProtocolException, IOException {
		// Given
		Delete("http://localhost:7919/test/url").addHeader(CORTX_HEADER, TEST_VALUE).execute();
		
		// When
		final Map<String, String> headers = cortx.retrieve().delete("/test/url").headers();
		
		// Then
		assertThat(headers.containsKey(CORTX_HEADER), is(true));
		assertThat(headers.get(CORTX_HEADER), is(TEST_VALUE));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void shouldNotAllowToRetrieveDeleteBody() throws ClientProtocolException, IOException {
		// Given/When/Then
		cortx.retrieve().delete("/test/url").body();
	}

	@Test
	public void shouldRetrieveHeadStatusCode() throws ClientProtocolException, IOException {
		// Given
		Head("http://localhost:7919/test/url").execute();
		
		// When
		final int status = cortx.retrieve().head("/test/url").statusCode();
		
		// Then
		assertThat(status, is(200));
	}
	
	@Test
	public void shouldRetrieveSpecifiedHeadStatusCode() throws ClientProtocolException, IOException {
		// Given
		cortx.on().head("/test/url").returnStatus(STATUS_204).response();
		Head("http://localhost:7919/test/url").execute();
		
		// When
		final int status = cortx.retrieve().head("/test/url").statusCode();
		
		// Then
		assertThat(status, is(STATUS_204));
	}
	
	@Test
	public void shouldRetrieveHeadStatusMessage() throws ClientProtocolException, IOException {
		// Given
		Head("http://localhost:7919/test/url").execute();
		
		// When
		final String message = cortx.retrieve().head("/test/url").statusMessage();
		
		// Then
		assertThat(message, is("OK"));
	}
	
	@Test
	public void shouldRetrieveSpecifiedHeadStatusMessage() throws ClientProtocolException, IOException {
		// Given
		cortx.on().head("/test/url").returnStatusMessage(TEST_VALUE).response();
		Head("http://localhost:7919/test/url").execute();
		
		// When
		final String message = cortx.retrieve().head("/test/url").statusMessage();
		
		// Then
		assertThat(message, is(TEST_VALUE));
	}
	
	@Test
	public void shouldRetrieveHeadHeader() throws ClientProtocolException, IOException {
		// Given
		Head("http://localhost:7919/test/url").addHeader(CORTX_HEADER, TEST_VALUE).execute();
		
		// When
		final String header = cortx.retrieve().head("/test/url").header(CORTX_HEADER);
		
		// Then
		assertThat(header, is(TEST_VALUE));
	}

	@Test
	public void shouldRetrieveHeadHeaders() throws ClientProtocolException, IOException {
		// Given
		Head("http://localhost:7919/test/url").addHeader(CORTX_HEADER, TEST_VALUE).execute();
		
		// When
		final Map<String, String> headers = cortx.retrieve().head("/test/url").headers();
		
		// Then
		assertThat(headers.containsKey(CORTX_HEADER), is(true));
		assertThat(headers.get(CORTX_HEADER), is(TEST_VALUE));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void shouldNotAllowToRetrieveHeadBody() throws ClientProtocolException, IOException {
		// Given/When/Then
		cortx.retrieve().head("/test/url").body();
	}

	@Test
	public void shouldRetrieveOptionsStatusCode() throws ClientProtocolException, IOException {
		// Given
		Options("http://localhost:7919/test/url").execute();
		
		// When
		final int status = cortx.retrieve().options("/test/url").statusCode();
		
		// Then
		assertThat(status, is(200));
	}
	
	@Test
	public void shouldRetrieveSpecifiedOptionsStatusCode() throws ClientProtocolException, IOException {
		// Given
		cortx.on().options("/test/url").returnStatus(STATUS_204).response();
		Options("http://localhost:7919/test/url").execute();
		
		// When
		final int status = cortx.retrieve().options("/test/url").statusCode();
		
		// Then
		assertThat(status, is(STATUS_204));
	}
	
	@Test
	public void shouldRetrieveOptionsStatusMessage() throws ClientProtocolException, IOException {
		// Given
		Options("http://localhost:7919/test/url").execute();
		
		// When
		final String message = cortx.retrieve().options("/test/url").statusMessage();
		
		// Then
		assertThat(message, is("OK"));
	}
	
	@Test
	public void shouldRetrieveSpecifiedOptionsStatusMessage() throws ClientProtocolException, IOException {
		// Given
		cortx.on().options("/test/url").returnStatusMessage(TEST_VALUE).response();
		Options("http://localhost:7919/test/url").execute();
		
		// When
		final String message = cortx.retrieve().options("/test/url").statusMessage();
		
		// Then
		assertThat(message, is(TEST_VALUE));
	}
	
	@Test
	public void shouldRetrieveOptionsHeader() throws ClientProtocolException, IOException {
		// Given
		Options("http://localhost:7919/test/url").addHeader(CORTX_HEADER, TEST_VALUE).execute();
		
		// When
		final String header = cortx.retrieve().options("/test/url").header(CORTX_HEADER);
		
		// Then
		assertThat(header, is(TEST_VALUE));
	}

	@Test
	public void shouldRetrieveOptionsHeaders() throws ClientProtocolException, IOException {
		// Given
		Options("http://localhost:7919/test/url").addHeader(CORTX_HEADER, TEST_VALUE).execute();
		
		// When
		final Map<String, String> headers = cortx.retrieve().options("/test/url").headers();
		
		// Then
		assertThat(headers.containsKey(CORTX_HEADER), is(true));
		assertThat(headers.get(CORTX_HEADER), is(TEST_VALUE));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void shouldNotAllowToRetrieveOptionsBody() throws ClientProtocolException, IOException {
		// Given/When/Then
		cortx.retrieve().options("/test/url").body();
	}

	@Test
	public void shouldRetrieveTraceStatusCode() throws ClientProtocolException, IOException {
		// Given
		Trace("http://localhost:7919/test/url").execute();
		
		// When
		final int status = cortx.retrieve().trace("/test/url").statusCode();
		
		// Then
		assertThat(status, is(200));
	}
	
	@Test
	public void shouldRetrieveSpecifiedTraceStatusCode() throws ClientProtocolException, IOException {
		// Given
		cortx.on().trace("/test/url").returnStatus(STATUS_204).response();
		Trace("http://localhost:7919/test/url").execute();
		
		// When
		final int status = cortx.retrieve().trace("/test/url").statusCode();
		
		// Then
		assertThat(status, is(STATUS_204));
	}
	
	@Test
	public void shouldRetrieveTraceStatusMessage() throws ClientProtocolException, IOException {
		// Given
		Trace("http://localhost:7919/test/url").execute();
		
		// When
		final String message = cortx.retrieve().trace("/test/url").statusMessage();
		
		// Then
		assertThat(message, is("OK"));
	}
	
	@Test
	public void shouldRetrieveSpecifiedTraceStatusMessage() throws ClientProtocolException, IOException {
		// Given
		cortx.on().trace("/test/url").returnStatusMessage(TEST_VALUE).response();
		Trace("http://localhost:7919/test/url").execute();
		
		// When
		final String message = cortx.retrieve().trace("/test/url").statusMessage();
		
		// Then
		assertThat(message, is(TEST_VALUE));
	}
	
	@Test
	public void shouldRetrieveTraceHeader() throws ClientProtocolException, IOException {
		// Given
		Trace("http://localhost:7919/test/url").addHeader(CORTX_HEADER, TEST_VALUE).execute();
		
		// When
		final String header = cortx.retrieve().trace("/test/url").header(CORTX_HEADER);
		
		// Then
		assertThat(header, is(TEST_VALUE));
	}

	@Test
	public void shouldRetrieveTraceHeaders() throws ClientProtocolException, IOException {
		// Given
		Trace("http://localhost:7919/test/url").addHeader(CORTX_HEADER, TEST_VALUE).execute();
		
		// When
		final Map<String, String> headers = cortx.retrieve().trace("/test/url").headers();
		
		// Then
		assertThat(headers.containsKey(CORTX_HEADER), is(true));
		assertThat(headers.get(CORTX_HEADER), is(TEST_VALUE));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void shouldNotAllowToRetrieveTraceBody() throws ClientProtocolException, IOException {
		// Given/When/Then
		cortx.retrieve().trace("/test/url").body();
	}
	
	@Test
	public void shouldRegisterBodyResponseOnGet() throws ClientProtocolException, IOException {
		// When
		cortx.on().get("/test/url").returnBody(CORTX_BODY).response();		
		final Response result = Get("http://localhost:7919/test/url").execute();
		
		// Then
		assertThat(result.returnContent().asString(), is(CORTX_BODY));
	}
	
	@Test
	public void shouldRegisterHeaderResponseOnGet() throws ClientProtocolException, IOException {
		// When
		cortx.on().get("/test/url").returnHeader(CORTX_HEADER, TEST_VALUE).response();		
		final Response result = Get("http://localhost:7919/test/url").execute();
		
		// Then
		assertThat(result.returnResponse().getFirstHeader(CORTX_HEADER).getValue(), is(TEST_VALUE));
	}
	
	@Test
	public void shouldRegisterStatusCodeResponseOnGet() throws ClientProtocolException, IOException {
		// When
		cortx.on().get("/test/url").returnStatus(STATUS_204).response();		
		final Response result = Get("http://localhost:7919/test/url").execute();
		
		// Then
		assertThat(result.returnResponse().getStatusLine().getStatusCode(), is(STATUS_204));
	}
	
	@Test
	public void shouldRegisterStatusMessageResponseOnGet() throws ClientProtocolException, IOException {
		// When
		cortx.on().get("/test/url").returnStatusMessage(TEST_VALUE).response();		
		final Response result = Get("http://localhost:7919/test/url").execute();
		
		// Then
		assertThat(result.returnResponse().getStatusLine().getReasonPhrase(), is(TEST_VALUE));
	}
	
	@Test
	public void shouldRegisterBodyResponseOnPost() throws ClientProtocolException, IOException {
		// When
		cortx.on().post("/test/url").returnBody(CORTX_BODY).response();		
		final Response result = Post("http://localhost:7919/test/url").execute();
		
		// Then
		assertThat(result.returnContent().asString(), is(CORTX_BODY));
	}
	
	@Test
	public void shouldRegisterHeaderResponseOnPost() throws ClientProtocolException, IOException {
		// When
		cortx.on().post("/test/url").returnHeader(CORTX_HEADER, TEST_VALUE).response();		
		final Response result = Post("http://localhost:7919/test/url").execute();
		
		// Then
		assertThat(result.returnResponse().getFirstHeader(CORTX_HEADER).getValue(), is(TEST_VALUE));
	}

	@Test
	public void shouldRegisterStatusCodeResponseOnPost() throws ClientProtocolException, IOException {
		// When
		cortx.on().post("/test/url").returnStatus(STATUS_204).response();		
		final Response result = Post("http://localhost:7919/test/url").execute();
		
		// Then
		assertThat(result.returnResponse().getStatusLine().getStatusCode(), is(STATUS_204));
	}
	
	@Test
	public void shouldRegisterStatusMessageResponseOnPost() throws ClientProtocolException, IOException {
		// When
		cortx.on().post("/test/url").returnStatusMessage(TEST_VALUE).response();		
		final Response result = Post("http://localhost:7919/test/url").execute();
		
		// Then
		assertThat(result.returnResponse().getStatusLine().getReasonPhrase(), is(TEST_VALUE));
	}
	
	@Test
	public void shouldRegisterBodyResponseOnPut() throws ClientProtocolException, IOException {
		// When
		cortx.on().put("/test/url").returnBody(CORTX_BODY).response();		
		final Response result = Put("http://localhost:7919/test/url").execute();
		
		// Then
		assertThat(result.returnContent().asString(), is(CORTX_BODY));
	}
	
	@Test
	public void shouldRegisterHeaderResponseOnPut() throws ClientProtocolException, IOException {
		// When
		cortx.on().put("/test/url").returnHeader(CORTX_HEADER, TEST_VALUE).response();		
		final Response result = Put("http://localhost:7919/test/url").execute();
		
		// Then
		assertThat(result.returnResponse().getFirstHeader(CORTX_HEADER).getValue(), is(TEST_VALUE));
	}

	@Test
	public void shouldRegisterStatusCodeResponseOnPut() throws ClientProtocolException, IOException {
		// When
		cortx.on().put("/test/url").returnStatus(STATUS_204).response();		
		final Response result = Put("http://localhost:7919/test/url").execute();
		
		// Then
		assertThat(result.returnResponse().getStatusLine().getStatusCode(), is(STATUS_204));
	}
	
	@Test
	public void shouldRegisterStatusMessageResponseOnPut() throws ClientProtocolException, IOException {
		// When
		cortx.on().put("/test/url").returnStatusMessage(TEST_VALUE).response();		
		final Response result = Put("http://localhost:7919/test/url").execute();
		
		// Then
		assertThat(result.returnResponse().getStatusLine().getReasonPhrase(), is(TEST_VALUE));
	}
	
	@Test
	public void shouldRegisterBodyResponseOnDelete() throws ClientProtocolException, IOException {
		// When
		cortx.on().delete("/test/url").returnBody(CORTX_BODY).response();		
		final Response result = Delete("http://localhost:7919/test/url").execute();
		
		// Then
		assertThat(result.returnContent().asString(), is(CORTX_BODY));
	}
	
	@Test
	public void shouldRegisterHeaderResponseOnDelete() throws ClientProtocolException, IOException {
		// When
		cortx.on().delete("/test/url").returnHeader(CORTX_HEADER, TEST_VALUE).response();		
		final Response result = Delete("http://localhost:7919/test/url").execute();
		
		// Then
		assertThat(result.returnResponse().getFirstHeader(CORTX_HEADER).getValue(), is(TEST_VALUE));
	}

	@Test
	public void shouldRegisterStatusCodeResponseOnDelete() throws ClientProtocolException, IOException {
		// When
		cortx.on().delete("/test/url").returnStatus(STATUS_204).response();		
		final Response result = Delete("http://localhost:7919/test/url").execute();
		
		// Then
		assertThat(result.returnResponse().getStatusLine().getStatusCode(), is(STATUS_204));
	}
	
	@Test
	public void shouldRegisterStatusMessageResponseOnDelete() throws ClientProtocolException, IOException {
		// When
		cortx.on().delete("/test/url").returnStatusMessage(TEST_VALUE).response();		
		final Response result = Delete("http://localhost:7919/test/url").execute();
		
		// Then
		assertThat(result.returnResponse().getStatusLine().getReasonPhrase(), is(TEST_VALUE));
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void shoulNotdRegisterBodyResponseOnHead() throws ClientProtocolException, IOException {
		// When / Then
		cortx.on().head("/test/url").returnBody(CORTX_BODY).response();
	}
	
	@Test
	public void shouldRegisterHeaderResponseOnHead() throws ClientProtocolException, IOException {
		// When
		cortx.on().head("/test/url").returnHeader(CORTX_HEADER, TEST_VALUE).response();		
		final Response result = Head("http://localhost:7919/test/url").execute();
		
		// Then
		assertThat(result.returnResponse().getFirstHeader(CORTX_HEADER).getValue(), is(TEST_VALUE));
	}

	@Test
	public void shouldRegisterStatusCodeResponseOnHead() throws ClientProtocolException, IOException {
		// When
		cortx.on().head("/test/url").returnStatus(STATUS_204).response();		
		final Response result = Head("http://localhost:7919/test/url").execute();
		
		// Then
		assertThat(result.returnResponse().getStatusLine().getStatusCode(), is(STATUS_204));
	}
	
	@Test
	public void shouldRegisterStatusMessageResponseOnHead() throws ClientProtocolException, IOException {
		// When
		cortx.on().head("/test/url").returnStatusMessage(TEST_VALUE).response();		
		final Response result = Head("http://localhost:7919/test/url").execute();
		
		// Then
		assertThat(result.returnResponse().getStatusLine().getReasonPhrase(), is(TEST_VALUE));
	}
	
	@Test
	public void shouldRegisterBodyResponseOnOptions() throws ClientProtocolException, IOException {
		// When
		cortx.on().options("/test/url").returnBody(CORTX_BODY).response();		
		final Response result = Options("http://localhost:7919/test/url").execute();
		
		// Then
		assertThat(result.returnContent().asString(), is(CORTX_BODY));
	}
	
	@Test
	public void shouldRegisterHeaderResponseOnOptions() throws ClientProtocolException, IOException {
		// When
		cortx.on().options("/test/url").returnHeader(CORTX_HEADER, TEST_VALUE).response();		
		final Response result = Options("http://localhost:7919/test/url").execute();
		
		// Then
		assertThat(result.returnResponse().getFirstHeader(CORTX_HEADER).getValue(), is(TEST_VALUE));
	}

	@Test
	public void shouldRegisterStatusCodeResponseOnOptions() throws ClientProtocolException, IOException {
		// When
		cortx.on().options("/test/url").returnStatus(STATUS_204).response();		
		final Response result = Options("http://localhost:7919/test/url").execute();
		
		// Then
		assertThat(result.returnResponse().getStatusLine().getStatusCode(), is(STATUS_204));
	}
	
	@Test
	public void shouldRegisterStatusMessageResponseOnOptions() throws ClientProtocolException, IOException {
		// When
		cortx.on().options("/test/url").returnStatusMessage(TEST_VALUE).response();		
		final Response result = Options("http://localhost:7919/test/url").execute();
		
		// Then
		assertThat(result.returnResponse().getStatusLine().getReasonPhrase(), is(TEST_VALUE));
	}
	
	@Test
	public void shouldRegisterBodyResponseOnTrace() throws ClientProtocolException, IOException {
		// When
		cortx.on().trace("/test/url").returnBody(CORTX_BODY).response();		
		final Response result = Trace("http://localhost:7919/test/url").execute();
		
		// Then
		assertThat(result.returnContent().asString(), is(CORTX_BODY));
	}
	
	@Test
	public void shouldRegisterHeaderResponseOnTrace() throws ClientProtocolException, IOException {
		// When
		cortx.on().trace("/test/url").returnHeader(CORTX_HEADER, TEST_VALUE).response();		
		final Response result = Trace("http://localhost:7919/test/url").execute();
		
		// Then
		assertThat(result.returnResponse().getFirstHeader(CORTX_HEADER).getValue(), is(TEST_VALUE));
	}

	@Test
	public void shouldRegisterStatusCodeResponseOnTrace() throws ClientProtocolException, IOException {
		// When
		cortx.on().trace("/test/url").returnStatus(STATUS_204).response();		
		final Response result = Trace("http://localhost:7919/test/url").execute();
		
		// Then
		assertThat(result.returnResponse().getStatusLine().getStatusCode(), is(STATUS_204));
	}
	
	@Test
	public void shouldRegisterStatusMessageResponseOnTrace() throws ClientProtocolException, IOException {
		// When
		cortx.on().trace("/test/url").returnStatusMessage(TEST_VALUE).response();		
		final Response result = Trace("http://localhost:7919/test/url").execute();
		
		// Then
		assertThat(result.returnResponse().getStatusLine().getReasonPhrase(), is(TEST_VALUE));
	}
	
}
