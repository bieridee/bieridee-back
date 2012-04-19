package ch.hsr.bieridee.test;

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

import ch.hsr.bieridee.config.Res;

/**
 * Test the creation of a new user via PUT on the user resource.
 * 
 */
public class UserPutTest {

	final String testUsername = "tester";
	final JSONObject userJson = new JSONObject();
	
	/**
	 * Test setup.
	 */
	@Before
	public void setUp() {
		try {
			this.userJson.put("username", this.testUsername);
			this.userJson.put("prename", "Stefan");
			this.userJson.put("surname", "Keller");
			this.userJson.put("email", "seff@openstreet.map");
			this.userJson.put("password", "7a6sdfp87ilovecats9difcapo43we");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests the creaton of a new user.
	 */
	@Test
	public void createAndGetCreatedUser() {
		final ClientResource clientResource = new ClientResource(Res.API_URL + "/users/" + this.testUsername);

		final Representation rep = new StringRepresentation(this.userJson.toString(), MediaType.APPLICATION_JSON);
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
			Assert.assertEquals(this.userJson.get("username"), newUser.get("username"));
			Assert.assertEquals(this.userJson.get("prename"), newUser.get("prename"));
			Assert.assertEquals(this.userJson.get("surname"), newUser.get("surname"));
			Assert.assertEquals(this.userJson.get("email"), newUser.get("email"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		clientResource.release();
	}
	
	/**
	 * Tests the update of the user.
	 */
	@Test
	public void updateAndGetUser() {
		final ClientResource clientResource = new ClientResource(Res.API_URL + "/users/" + this.testUsername);
		
		
		final JSONObject partialUserJson = new JSONObject();
		try {
			partialUserJson.put("username", "notpossibletoupdate");
			partialUserJson.put("prename", "Danilo");
			partialUserJson.put("surname", "Bargen");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		final Representation rep = new StringRepresentation(partialUserJson.toString(), MediaType.APPLICATION_JSON);
		clientResource.put(rep);
		
		final Representation updatedUserRep = clientResource.get(MediaType.APPLICATION_JSON);
		JSONObject updatedUser = null;
		try {
			updatedUser = new JSONObject(updatedUserRep.getText());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		Assert.assertNotNull(updatedUser);
		try {
			Assert.assertEquals(this.testUsername, updatedUser.get("username"));
			Assert.assertEquals(partialUserJson.get("prename"), updatedUser.get("prename"));
			Assert.assertEquals(partialUserJson.get("surname"), updatedUser.get("surname"));
			Assert.assertEquals(this.userJson.get("email"), updatedUser.get("email"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		clientResource.release();
	}
}
