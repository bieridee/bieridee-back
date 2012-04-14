package ch.hsr.bieridee.models;

import org.neo4j.graphdb.Node;

import ch.hsr.bieridee.domain.User;
import ch.hsr.bieridee.utils.DBUtil;

/**
 * Model to work with and persist the User object.
 * 
 * @author jfurrer
 *
 */
public class UserModel extends AbstractModel {
	
	private User domainObject;
	private Node node;
	
	/**
	 * Creates a UserModel, consisting from a User domain object and the corresponding Node.
	 * 
	 * @param id Id of the beer
	 */
	public UserModel(long id) {
		this(DBUtil.getNodeById(id));
	}
	
	/**
	 * Creates a UserModel, consisting from a User domain object and the corresponding Node.
	 * 
	 * @param node Usernode
	 */
	public UserModel(Node node) {
		this.node = node;
		final String username = (String) this.node.getProperty("username");
		final String firstname = (String) this.node.getProperty("firstname");
		final String lastname = (String) this.node.getProperty("lastname");
		final String password = (String) this.node.getProperty("password");
		final String email = (String) this.node.getProperty("email");
		
		this.domainObject = new User(username, password, firstname, lastname, email);
		
	}
	
	
	
}
