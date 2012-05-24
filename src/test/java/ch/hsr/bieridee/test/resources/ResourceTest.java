package ch.hsr.bieridee.test.resources;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpResponseException;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;

import ch.hsr.bieridee.test.ServerTest;
import ch.hsr.bieridee.test.http.AuthJsonHttp;
import ch.hsr.bieridee.test.http.HttpHelper;

/**
 * Baseclass for all resource tests.
 */
public abstract class ResourceTest extends ServerTest {

	private String get(String uri) {
		final HttpHelper httpHelper = AuthJsonHttp.create();
		final HttpResponse response = httpHelper.get(uri);
		try {
			return new BasicResponseHandler().handleResponse(response);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected HttpResponse getResponse(String uri) {
		final HttpHelper httpHelper = AuthJsonHttp.create();
		return httpHelper.get(uri);
	}

	protected JSONObject getJSONObject(String uri) {
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(this.get(uri));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		Assert.assertNotNull(jsonObject);
		return jsonObject;
	}
	
	protected JSONArray getJSONArray(String uri) {
		JSONArray jsonArray = null;
		try {
			jsonArray = new JSONArray(this.get(uri));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		Assert.assertNotNull(jsonArray);
		return jsonArray;
	}

	protected String putJSON(String uri, JSONObject object) {
		final HttpHelper httpHelper = AuthJsonHttp.create();
		final HttpResponse response;
		try {
			response = httpHelper.put(uri, object);
			return new BasicResponseHandler().handleResponse(response);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (HttpResponseException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected String postJson(String uri, JSONObject object) {
		final HttpHelper httpHelper = AuthJsonHttp.create();
		final HttpResponse response;
		try {
			response = httpHelper.post(uri, object);
			return new BasicResponseHandler().handleResponse(response);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (HttpResponseException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
