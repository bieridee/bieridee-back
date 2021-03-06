package ch.hsr.bieridee.test.resources;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;

import ch.hsr.bieridee.config.NodeProperty;
import ch.hsr.bieridee.test.ServerTest;
import ch.hsr.bieridee.test.helpers.Helpers;

/**
 * Test the creation of a new user via PUT on the user resource.
 * 
 */
public class UserPutTest extends ResourceTest {

	final String testUsername = "tester";
	final JSONObject userJson = new JSONObject();

	/**
	 * Test setup.
	 */
	@Before
	public void setUp() {
		try {
			this.userJson.put(NodeProperty.User.USERNAME, this.testUsername);
			this.userJson.put(NodeProperty.User.PRENAME, "Stefan");
			this.userJson.put(NodeProperty.User.SURNAME, "Keller");
			this.userJson.put(NodeProperty.User.EMAIL, "seff@openstreet.map");
			this.userJson.put(NodeProperty.User.PASSWORD, "7a6sdfp87ilovecats9difcapo43we");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests the creation of a new user.
	 */
	@Test
	public void createAndGetCreatedUser() {
		final String uri = Helpers.buildResourceUri("/users/" + this.testUsername);
		final ClientResource clientResource = new ClientResource(uri);

		final Representation rep = new StringRepresentation(this.userJson.toString(), MediaType.APPLICATION_JSON);
		clientResource.put(rep);

		JSONObject newUser = this.getJSONObject(uri);

		Assert.assertNotNull(newUser);
		try {
			Assert.assertEquals(this.userJson.get(NodeProperty.User.USERNAME), newUser.get(NodeProperty.User.USERNAME));
			Assert.assertEquals(this.userJson.get(NodeProperty.User.PRENAME), newUser.get(NodeProperty.User.PRENAME));
			Assert.assertEquals(this.userJson.get(NodeProperty.User.SURNAME), newUser.get(NodeProperty.User.SURNAME));
			Assert.assertEquals(this.userJson.get(NodeProperty.User.EMAIL), newUser.get(NodeProperty.User.EMAIL));
		} catch (JSONException e) {
			Assert.fail();
			e.printStackTrace();
		}

		clientResource.release();
	}

	/**
	 * Tests the update of the user.
	 */
	@Test
	public void updateAndGetUser() {
		final String uri = Helpers.buildResourceUri("/users/" + this.testUsername);

		final JSONObject partialUserJson = new JSONObject();
		try {
			partialUserJson.put(NodeProperty.User.USERNAME, "notpossibletoupdate");
			partialUserJson.put(NodeProperty.User.PRENAME, "DJ");
			partialUserJson.put(NodeProperty.User.SURNAME, "Bobo");
		} catch (JSONException e) {
			Assert.fail();
			e.printStackTrace();
		}

		this.putJSON(uri, partialUserJson);

		JSONObject updatedUser = this.getJSONObject(uri);

		Assert.assertNotNull(updatedUser);
		try {
			Assert.assertEquals(this.testUsername, updatedUser.get(NodeProperty.User.USERNAME));
			Assert.assertEquals(partialUserJson.get(NodeProperty.User.PRENAME), updatedUser.get(NodeProperty.User.PRENAME));
			Assert.assertEquals(partialUserJson.get(NodeProperty.User.SURNAME), updatedUser.get(NodeProperty.User.SURNAME));
			Assert.assertEquals(this.userJson.get(NodeProperty.User.EMAIL), updatedUser.get(NodeProperty.User.EMAIL));
		} catch (JSONException e) {
			Assert.fail();
			e.printStackTrace();
		}
	}
}
