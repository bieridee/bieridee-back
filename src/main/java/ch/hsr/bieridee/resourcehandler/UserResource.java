package ch.hsr.bieridee.resourcehandler;

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
}
