package ch.hsr.bieridee.resourcehandler;

import java.util.List;

import org.neo4j.graphdb.Node;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.domain.User;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BeerModel;
import ch.hsr.bieridee.resourcehandler.interfaces.IUserRessource;
import ch.hsr.bieridee.utils.Cypher;
import ch.hsr.bieridee.utils.DomainConverter;

/**
 * Server resource to provide access to users.
 * 
 * @author cfaessle
 * 
 */
public class UserRessource extends ServerResource implements IUserRessource {

	@Override
	public String retrieve() {
		final String usernameParam = (String) this.getRequestAttributes().get("username");
		System.out.println("Username: " + usernameParam);

		//final String cypherQuery = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_USER]-USER_INDEX-[:INDEXES]->User WHERE User.username=\'" + usernameParam + "\' RETURN User";
		final String cypherQuery = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_USER]-USER_INDEX-[:INDEXES]-User-[:HAS_CONSUMED]->Beer WHERE User.username=\'jfurrer\' RETURN Beer";
		final List<Node> result = Cypher.executeAndGetNodes(cypherQuery, "Beer");
		List<BeerModel> models = null;
		try {
			models = DomainConverter.createBeerModelsFromList(result);
		} catch (WrongNodeTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//final Node result = Cypher.executeAndGetSingleNode(cypherQuery, "User");
		
		//final User user = createDummyUser(result);
		
		return models.toString();
	}

	private User createDummyUser(final Node result) {
		final String username = (String) result.getProperty("username");
		final String password = "hidden";
		final String firstName = (String) result.getProperty("prename");
		final String lastName = (String) result.getProperty("surname");
		final String emailAddress = (String) result.getProperty("email");
		final User user = new User(username, password, firstName, lastName, emailAddress);
		return user;
	}

}
