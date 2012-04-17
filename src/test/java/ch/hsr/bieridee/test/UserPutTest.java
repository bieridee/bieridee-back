package ch.hsr.bieridee.test;

import static org.junit.Assert.*;

import org.codehaus.groovy.tools.shell.commands.ClearCommand;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;
import org.restlet.data.MediaType;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;

import ch.hsr.bieridee.config.Res;

public class UserPutTest {

	@Test
	public void test() {
		final ClientResource clientResource = new ClientResource(Res.API_URL + Res.USER_DOCUMENT);
		
		JSONObject user = new JSONObject();
		try {
			user.put("username", "tester");
			user.put("prename", "robert");
			user.put("surname", "huber");
			user.put("email", "robert@googl.exe");
			user.put("password", "7a6sdfp87r87qow9r5809difcapowe");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		final Representation rep = new StringRepresentation(user.toString(), MediaType.APPLICATION_JSON);
		clientResource.put(rep);
		
	}

}
