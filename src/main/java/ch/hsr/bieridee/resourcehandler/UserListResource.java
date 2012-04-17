package ch.hsr.bieridee.resourcehandler;

import java.util.List;

import org.neo4j.graphdb.Node;
import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.data.Status;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.domain.User;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.UserModel;
import ch.hsr.bieridee.resourcehandler.interfaces.IUserListResource;
import ch.hsr.bieridee.utils.DBUtil;
import ch.hsr.bieridee.utils.DomainConverter;

/**
 * User list resource.
 *
 */
public class UserListResource extends ServerResource implements IUserListResource {

	@Override
	public Representation retrieve() {
		final List<Node> userNodes = DBUtil.getUserNodeList();
		List<UserModel> userModels = null;
		
		try {
			userModels = DomainConverter.createUserModelsFromList(userNodes);
		} catch (WrongNodeTypeException e) {
			setStatus(Status.CLIENT_ERROR_NOT_FOUND, e, e.getMessage());
			return null;
		}
		
		final List<User> userList = DomainConverter.extractDomainObjectFromModel(userModels);
		final User[] users = userList.toArray(new User[userList.size()]);
		
		final JacksonRepresentation<User[]> usersJacksonRep = new JacksonRepresentation<User[]>(users);
		usersJacksonRep.setObjectMapper(Config.getObjectMapper());
		
		return usersJacksonRep;
	}

}
