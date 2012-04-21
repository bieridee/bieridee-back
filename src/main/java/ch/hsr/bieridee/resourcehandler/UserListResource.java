package ch.hsr.bieridee.resourcehandler;

import java.util.List;

import org.neo4j.graphdb.Node;
import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.domain.User;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.UserModel;
import ch.hsr.bieridee.resourcehandler.interfaces.IReadOnlyResource;
import ch.hsr.bieridee.utils.DBUtil;
import ch.hsr.bieridee.utils.DomainConverter;

/**
 * User list resource.
 *
 */
public class UserListResource extends ServerResource implements IReadOnlyResource {

	@Override
	public Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException {
		final List<Node> userNodes = DBUtil.getUserNodeList();
		final List<UserModel> userModels = DomainConverter.createUserModelsFromList(userNodes);
		final List<User> userList = DomainConverter.extractDomainObjectFromModel(userModels);
		final User[] users = userList.toArray(new User[userList.size()]);
		
		final JacksonRepresentation<User[]> usersJacksonRep = new JacksonRepresentation<User[]>(users);
		usersJacksonRep.setObjectMapper(Config.getObjectMapper());
		
		return usersJacksonRep;
	}

}
