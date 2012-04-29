package ch.hsr.bieridee.resourcehandler;

import java.util.List;

import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.UserModel;
import ch.hsr.bieridee.resourcehandler.interfaces.IReadOnlyResource;

/**
 * User list resource.
 *
 */
public class UserListResource extends ServerResource implements IReadOnlyResource {

	@Override
	public Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException {
		final List<UserModel> userModels = UserModel.getAll();
		final UserModel[] userModelArray = userModels.toArray(new UserModel[userModels.size()]);
		
		final JacksonRepresentation<UserModel[]> usersJacksonRep = new JacksonRepresentation<UserModel[]>(userModelArray);
		usersJacksonRep.setObjectMapper(Config.getObjectMapper());
		
		return usersJacksonRep;
	}

}
