package ch.hsr.bieridee.resourcehandler;

import java.io.IOException;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;
import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.domain.User;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.UserModel;
import ch.hsr.bieridee.resourcehandler.interfaces.IUserRessource;
import ch.hsr.bieridee.utils.DBUtil;

/**
 * Server resource to provide access to users.
 * 
 * @author cfaessle
 * 
 */
public class UserResource extends ServerResource implements IUserRessource {
	
	private String username;
	
	@Override
	public void doInit() {
		this.username = (String) getRequestAttributes().get(Res.USER_REQ_ATTR);
	}

	@Override
	public Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException {
		final UserModel userModel = new UserModel(this.username);
		
		final User user = userModel.getDomainObject();
		
		final JacksonRepresentation<User> userJacksonRep = new JacksonRepresentation<User>(user);
		userJacksonRep.setObjectMapper(Config.getObjectMapper());
		
		return userJacksonRep;
		
	}
	
	@Override
	public void store(Representation user) {
		UserModel userModel = null;
		JSONObject userJson = null;
		try {
			userJson = new JSONObject(user.getText());
			 userModel = new UserModel(this.username);
		} catch (NotFoundException e) {
			this.createNewUser(userJson);
		} catch (WrongNodeTypeException e) {
			// should not happen ;)
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// update user 
		try {
			userJson = new JSONObject(user.getText());
			userModel.setUsername(userJson.getString("username"));
			userModel.setPrename(userJson.getString("prename"));
			userModel.setSurname(userJson.getString("surname"));
			userModel.setEmail(userJson.getString("email"));
			userModel.setPassword(userJson.getString("password"));
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private void createNewUser(JSONObject user) {
		String username = null;
		String prename = null;
		String surname = null;
		String email = null;
		String password = null;
		
		try {
			username = user.getString("username");
			prename = user.getString("prename");
			surname = user.getString("surname");
			email = user.getString("email");
			password = user.getString("password");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		final User userObject = new User(username, password,prename, surname, email);
		
		
	}
}
