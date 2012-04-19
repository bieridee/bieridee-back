package ch.hsr.bieridee.resourcehandler;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.graphdb.NotFoundException;
import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.data.Status;
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
 */
public class UserResource extends ServerResource implements IUserRessource {

	private static final Logger LOG = Logger.getLogger(UserResource.class);
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
	public void store(Representation user) throws JSONException, IOException {

		final JSONObject userJson = new JSONObject(user.getText());

		if (DBUtil.doesUserExist(this.username)) {
			updateUser(userJson);
		} else {
			createNewUser(userJson);
		}

	}

	private void updateUser(JSONObject userJson) throws JSONException {
		UserModel userModel = null;

		try {
			userModel = new UserModel(this.username);
		} catch (NotFoundException e) {
			LOG.error("The user " + this.username + " does not exists eventhoug it should", e);
			setStatus(Status.SERVER_ERROR_INTERNAL, e, e.getMessage());
		} catch (WrongNodeTypeException e) {
			LOG.error("The user " + this.username + " does not exists eventhoug it should", e);
			setStatus(Status.SERVER_ERROR_INTERNAL, e, e.getMessage());
		}
		
		// update the values provided by the client, the username is not updatable
		if(userJson.has("prename")) {
			userModel.setPrename(userJson.getString("prename"));
		}
		if(userJson.has("surname")) {
			userModel.setSurname(userJson.getString("surname"));
		}
		if(userJson.has("email")) {
			userModel.setEmail(userJson.getString("email"));
		}
		if(userJson.has("password")) {
			userModel.setPassword(userJson.getString("password"));
		}
		
		setStatus(Status.SUCCESS_CREATED);
	}

	private void createNewUser(JSONObject userJson) throws JSONException {
		final String username = userJson.getString("username");
		final String prename = userJson.getString("prename");
		final String surname = userJson.getString("surname");
		final String email = userJson.getString("email");
		final String password = userJson.getString("password");

		final User userObject = new User(username, password, prename, surname, email);

		UserModel.create(userObject);

		setStatus(Status.SUCCESS_CREATED);
	}
}
