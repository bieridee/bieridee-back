package ch.hsr.bieridee.test;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;

import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.utils.NodeProperty;

/**
 * Test the creation of a new user via PUT on the user resource.
 * 
 */
public class UserPutTest {

	final String testUsername = "tester";

	private JSONObject getUserObject() {
		final JSONObject user = new JSONObject();
		try {
			user.put(NodeProperty.User.USERNAME, this.testUsername);
			user.put(NodeProperty.User.PRENAME, "Stefan");
			user.put(NodeProperty.User.SURNAME, "Keller");
			user.put(NodeProperty.User.EMAIL, "seff@openstreet.map");
			user.put(NodeProperty.User.PASSWORD, "7a6sdfp87ilovecats9difcapo43we");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return user;
	}

	/**
	 * Tests the creaton of a new user.
	 */
	@Test
	public void createAndGetCreatedUser() {
		final ClientResource clientResource = new ClientResource(Res.API_URL + "/users/" + this.testUsername);

		final JSONObject user = this.getUserObject();

		final Representation rep = new StringRepresentation(user.toString(), MediaType.APPLICATION_JSON);
		clientResource.put(rep);

		final Representation newUserRep = clientResource.get(MediaType.APPLICATION_JSON);
		JSONObject newUser = null;
		try {
			newUser = new JSONObject(newUserRep.getText());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		Assert.assertNotNull(newUser);
		try {
			Assert.assertEquals(user.get(NodeProperty.User.USERNAME), newUser.get(NodeProperty.User.USERNAME));
			Assert.assertEquals(user.get(NodeProperty.User.PRENAME), newUser.get(NodeProperty.User.PRENAME));
			Assert.assertEquals(user.get(NodeProperty.User.SURNAME), newUser.get(NodeProperty.User.SURNAME));
			Assert.assertEquals(user.get(NodeProperty.User.EMAIL), newUser.get(NodeProperty.User.EMAIL));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		clientResource.release();

	}

}
