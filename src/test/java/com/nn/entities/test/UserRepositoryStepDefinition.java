package com.nn.entities.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.core.MediaType;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.AfterClass;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class UserRepositoryStepDefinition {

	private static final String URI = "uri";
	private static final String API = "apiroot";
	private static final String LOCALHOST = "localhost";
	private static final int PORT = 8080;
	private static final String HTTP = "http";
	private static final String JSON_ROOT = "json_root";
	private static final String USERS = "users_repository_url";
	private static Map<String, String> repositoryURLS = new HashMap<String, String>();
	private static volatile int lastResponseCode = 0;

	@Given("^the api context URI is '([^']+)'$")
	public void setURI(String uri) throws Throwable {
		Environment.setProperty(URI, uri);
	}

	@And("^the rest endpoint is that '([^']+)'$")
	public void getURIBuilder(String apiRoot) throws Throwable {
		Environment.setProperty(API, apiRoot);
	}

	@When("^the user queries the URI '([^']+)'$")
	public void setAPI(String api) throws Throwable {
		Environment.setProperty(API, api);
	}

	@Then("^the user should get a response code '(\\d+)' and number of '([^']+)' should be '([^']+)' upon querying the rest api root$")
	public void getRequestOnJSONRootURL(int responseCode, String jsonRoot, String expression) throws Throwable {
		Environment.setProperty(JSON_ROOT, jsonRoot);

		URIBuilder builder = new URIBuilder();
		builder.setScheme(HTTP).setHost(LOCALHOST).setPort(PORT)
				.setPath(Environment.getProperty(URI) + Environment.getProperty(API));
		HttpGet getRequest = new HttpGet(builder.build());
		getRequest.addHeader("accept", MediaType.APPLICATION_JSON);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = httpClient.execute(getRequest);
		DocumentContext jsonContext = null;
		try {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				jsonContext = JsonPath.parse(instream);
			}
		} finally {
			response.close();
		}
		assertTrue(response.getStatusLine().getStatusCode() == responseCode);
		int length = jsonContext.read("$['" + jsonRoot + "'].length()", Integer.class);
		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression(length + expression);
		assertTrue(exp.getValue(Boolean.class));
		@SuppressWarnings("rawtypes")
		Map<String, Map> m = jsonContext.read("$['" + jsonRoot + "']");
		for (String key : m.keySet()) {
			@SuppressWarnings("unchecked")
			String url = ((Map<String, String>) m.get(key)).get("href");
			url = url.replaceAll("\\{.*", "");
			repositoryURLS.put(key, url);
		}
		System.out.println(repositoryURLS);
	}

	@When("^RequestMethod '([^']+)' executed on '([^']+)' with 'id' as '([^']*)', '([^']+)' as '([^']*)' and '([^']+)' as '([^']*)'$")
	public void user_create_upate(String requestMethod, String uri, String id, String param1, String param1value,
			String param2, String param2value) throws Throwable {
		Environment.setProperty(USERS, uri);

		HttpUriRequest request = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();

		StringBuilder sb = new StringBuilder("{");
		if (param1value != null) {
			sb.append(" \"").append(param1).append("\" : \"").append(param1value).append("\"");
			sb.append(",");
		}
		if (param2value != null) {
			sb.append(" \"").append(param2).append("\" : \"").append(param2value).append("\"");
		}
		sb.append("}");
		StringEntity user = new StringEntity(sb.toString());
		System.out.println(sb.toString());

		String requestURI = repositoryURLS.get(uri);
		if (id.length() != 0) {
			requestURI = requestURI + "/" + id;
		}

		switch (requestMethod) {
		case "POST":
			request = new HttpPost(requestURI);
			((HttpPost) request).setEntity(user);
			break;
		case "PATCH":
			request = new HttpPatch(requestURI);
			((HttpPatch) request).setEntity(user);
			break;
		case "DELETE":
			request = new HttpDelete(requestURI);
			break;
		}
		request.setHeader("Accept", MediaType.APPLICATION_JSON);
		request.setHeader("Content-type", MediaType.APPLICATION_JSON);

		CloseableHttpResponse response = httpClient.execute(request);
		DocumentContext jsonContext = null;
		try {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				jsonContext = JsonPath.parse(instream);
			}
		} finally {
			response.close();
		}
		lastResponseCode = response.getStatusLine().getStatusCode();

	}

	@Then("^the request should return http code '(\\d+)'$")
	public void checkHTTPResponseCode(int code) throws Throwable {
		assertTrue(code == lastResponseCode);
	}

	@AfterClass
	public void afterScenario() throws Throwable {
		Connection con = null;
		Statement st = null;

		InputStream is = this.getClass().getResourceAsStream("/app.properties");
		Properties prop = (new Properties());
		prop.load(is);
		String url = prop.getProperty("spring.datasource.url").replace("postgres.db", "localhost");
		String user = prop.getProperty("spring.datasource.username");
		String password = prop.getProperty("spring.datasource.password");
		con = DriverManager.getConnection(url, user, password);
		st = con.createStatement();
		st.executeUpdate("delete from nn_user");
		st.executeQuery("select setval('nn_user_id_seq',1)");
		con.close();
	}
}
