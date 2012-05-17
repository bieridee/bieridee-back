package ch.hsr.bieridee.resourcehandler;


import java.io.IOException;

import ch.hsr.bieridee.auth.HmacSha256Verifier;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.graphdb.NotFoundException;
import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.data.Status;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.config.NodeProperty;
import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.UserModel;
import ch.hsr.bieridee.resourcehandler.interfaces.IStoreResource;

/**
 * Server resource to provide access to users.
 *
 * <b>Important:</b> This resource is not behind the guard,
 * don't forget to manually check authentication!
 *
 * Authentication works as follows:
 * - If the request is signed, treat PUT as "update".
 * - If it's unsigned, treat PUT as "create".
 */
public class UserResource extends ServerResource implements IStoreResource {

	private static final Logger LOG = Logger.getLogger(UserResource.class);
	private String username;
	private int authStatus;

	@Override
	public void doInit() {
		this.authStatus = new HmacSha256Verifier().verify(getRequest(), getResponse());
		this.username = (String) getRequestAttributes().get(Res.USER_REQ_ATTR);
	}

	@Override
	public Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException, JSONException {
		if (this.authStatus != HmacSha256Verifier.RESULT_VALID) {
			final String message = "Invalid or missing credentials.";
			this.respondWith(Status.CLIENT_ERROR_UNAUTHORIZED, message);
			return getResponseEntity();
		}
		final UserModel userModel = new UserModel(this.username);
		final JacksonRepresentation<UserModel> userJacksonRep = new JacksonRepresentation<UserModel>(userModel);
		userJacksonRep.setObjectMapper(Config.getObjectMapper());
		return userJacksonRep;
	}

	@Override
	public void store(Representation user) throws JSONException, IOException {
		if (user == null) {
			this.respondWith(Status.CLIENT_ERROR_BAD_REQUEST, "Request data missing.");
			return;
		}
		final JSONObject userJson = new JSONObject(user.getText());

		// If no auth header is present, create new user
		if (this.authStatus == HmacSha256Verifier.RESULT_MISSING) {
			if (UserModel.doesUserExist(this.username)) {
				final String message = "User " + this.username + " already exists. ";
				this.respondWith(Status.CLIENT_ERROR_CONFLICT, message);
				return;
			} else {
				createNewUser(userJson);
			}

		// If auth header is present and valid, update user
		} else if (this.authStatus == HmacSha256Verifier.RESULT_VALID) {
			if (UserModel.doesUserExist(this.username)) {
				updateUser(userJson);
			} else {
				final String message = "User " + this.username + " not found. ";
				this.respondWith(Status.CLIENT_ERROR_NOT_FOUND, message);
				return;
			}

		// If auth header is present and invalid, return error
		} else {
			final String message = "Invalid or missing credentials.";
			this.respondWith(Status.CLIENT_ERROR_UNAUTHORIZED, message);
			return;
		}
	}

	@Override
	public void remove(Representation rep) throws JSONException {
		if (this.authStatus != HmacSha256Verifier.RESULT_VALID) {
			final String message = "Invalid or missing credentials.";
			this.respondWith(Status.CLIENT_ERROR_UNAUTHORIZED, message);
			return;
		}
		throw new NotImplementedException(); // TODO implement
	}

	/**
	 * Update a user with changed data.
	 * @param userJson The user JSONObject. Might contain only partial data.
	 * @throws JSONException
	 */
	private void updateUser(JSONObject userJson) throws JSONException {
		UserModel userModel = null;

		try {
			userModel = new UserModel(this.username);
		} catch (NotFoundException e) {
			LOG.error("The user " + this.username + " does not exists even though it should", e);
			setStatus(Status.SERVER_ERROR_INTERNAL, e, e.getMessage());
		} catch (WrongNodeTypeException e) {
			LOG.error("The user " + this.username + " does not exists even though it should", e);
			setStatus(Status.SERVER_ERROR_INTERNAL, e, e.getMessage());
		}
		
		// Update the values provided by the client, the username is not updatable
		if(userJson.has(NodeProperty.User.PRENAME)) {
			userModel.setPrename(userJson.getString(NodeProperty.User.PRENAME));
		}
		if(userJson.has(NodeProperty.User.SURNAME)) {
			userModel.setSurname(userJson.getString(NodeProperty.User.SURNAME));
		}
		if(userJson.has(NodeProperty.User.EMAIL)) {
			userModel.setEmail(userJson.getString(NodeProperty.User.EMAIL));
		}
		if(userJson.has(NodeProperty.User.PASSWORD)) {
			userModel.setPassword(userJson.getString(NodeProperty.User.PASSWORD));
		}
		
		setStatus(Status.SUCCESS_NO_CONTENT);
	}

	/**
	 * Create a new user with the specified data.
	 * @param userJson The user JSONObject.
	 * @throws JSONException
	 */
	private void createNewUser(JSONObject userJson) throws JSONException {
		try {
			final String username = this.username;
			final String prename = userJson.getString(NodeProperty.User.PRENAME);
			final String surname = userJson.getString(NodeProperty.User.SURNAME);
			final String email = userJson.getString(NodeProperty.User.EMAIL);
			final String password = userJson.getString(NodeProperty.User.PASSWORD);
			UserModel.create(username, password, prename, surname, email);
			setStatus(Status.SUCCESS_CREATED);
		} catch (JSONException e) {
			final String message = e.getMessage();
			this.respondWith(Status.CLIENT_ERROR_BAD_REQUEST, message);
		}
	}

	/**
	 * Respond with the specified status code and body message.
	 *
	 * @param status The HTTP response status code
	 * @param message The message to add to the body.
	 */
	private void respondWith(Status status, String message) throws JSONException {
		setStatus(status);
		final JSONObject body = new JSONObject();
		body.put("error", message);
		final JsonRepresentation entity = new JsonRepresentation(body);
		getResponse().setEntity(entity);
	}
}
