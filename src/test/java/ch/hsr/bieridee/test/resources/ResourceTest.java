package ch.hsr.bieridee.test.resources;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;

import ch.hsr.bieridee.test.ServerTest;

public abstract class ResourceTest extends ServerTest {
	/**
	 * Returns the JSONObject for a specific URI.
	 * 
	 * @param uri
	 *            The resource URI
	 * @return JSON representation of the resource
	 */
	protected JSONObject getJSON(String uri) {
		final ClientResource clientResource = new ClientResource(uri);
		final Representation response = clientResource.get(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(response.getText());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Assert.assertNotNull(jsonObject);
		clientResource.release();
		return jsonObject;
	}

	protected void putJSON(String uri, JSONObject object) {
		final ClientResource clientResource = new ClientResource(uri);
		final Representation rep = new StringRepresentation(object.toString(), MediaType.APPLICATION_JSON);
		clientResource.put(rep);
		clientResource.release();
	}
}