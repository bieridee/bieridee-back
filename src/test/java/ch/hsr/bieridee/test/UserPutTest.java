package ch.hsr.bieridee.test;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.neo4j.graphdb.NotFoundException;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;

import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.UserModel;

public class UserPutTest {

	final String testUsername = "tester";

	@Test
	public void createAndGetCreatedUser() {
		final ClientResource clientResource = new ClientResource(Res.API_URL + "/users/"+testUsername);

		JSONObject user = new JSONObject();
		try {
			user.put("username", testUsername);
			user.put("prename", "robert");
			user.put("surname", "huber");
			user.put("email", "robert@googl.exe");
			user.put("password", "7a6sdfp87r87qow9r5809difcapowe");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		final Representation rep = new StringRepresentation(user.toString(), MediaType.APPLICATION_JSON);
		clientResource.put(rep);

		UserModel createUserModel = null;
		try {
			createUserModel = new UserModel(testUsername);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WrongNodeTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertNotNull(createUserModel);
		Assert.assertEquals(testUsername, createUserModel.getPrename());

	}

}
