package ch.hsr.bieridee.models;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;

import ch.hsr.bieridee.config.NodeType;
import ch.hsr.bieridee.domain.User;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
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
	 * @param name Username of the user
	 * @throws WrongNodeTypeException Thrown if the name does not reference a user node
	 */
	public UserModel(String name) throws WrongNodeTypeException {
		this(DBUtil.getUserByName(name));
	}
	
	/**
	 * Creates a UserModel, consisting from a User domain object and the corresponding Node.
	 * 
	 * @param node Usernode
	 * @throws WrongNodeTypeException Thrown if the given node is not of type user
	 */
	public UserModel(Node node) throws WrongNodeTypeException {
		checkNodeType(node);
		
		this.node = node;
		final String username = (String) this.node.getProperty("username");
		final String firstname = (String) this.node.getProperty("prename");
		final String lastname = (String) this.node.getProperty("surname");
		final String password = (String) this.node.getProperty("password");
		final String email = (String) this.node.getProperty("email");
		
		this.domainObject = new User(username, password, firstname, lastname, email);
		
	}
	
	public User getDomainObject() {
		return this.domainObject;
	}
	
	public Node getNode() {
		return this.node;
	}
	
	public String getUsername() {
		return this.domainObject.getUsername();
	}
	
	//SUPPRESS CHECKSTYLE: setter
	public void setUsername(String username) {
		this.domainObject.setUsername(username);
		this.node.setProperty("username", username);
	}
	
	public String getPrename() {
		return this.domainObject.getPrename();
	}
	
	//SUPPRESS CHECKSTYLE: setter
	public void setPrename(String prename) {
		this.domainObject.setPrename(prename);
		this.node.setProperty("prename", prename);
	}
	
	public String getSurname() {
		return this.domainObject.getSurname();
	}
	
	//SUPPRESS CHECKSTYLE: setter
	public void setSurname(String surname) {
		this.domainObject.setSurname(surname);
		this.node.setProperty("surname", surname);
	}
	
	public String getPassword() {
		return this.domainObject.getPassword();
	}
	
	//SUPPRESS CHECKSTYLE: setter
	public void setPassword(String password) {
		this.domainObject.setSurname(password);
		this.node.setProperty("password", password);
	}
	
	public String getEmail() {
		return this.domainObject.getEmail();
	}
	
	//SUPPRESS CHECKSTYLE: setter
	public void setEmail(String email) {
		this.domainObject.setEmail(email);
		this.node.setProperty("email", email);
	}
	
	private void checkNodeType(Node node) throws WrongNodeTypeException {
		String type = null;
		try {
			type = (String) node.getProperty("type");
		} catch (NotFoundException e) {
			throw new WrongNodeTypeException(e);
		}
		if(!NodeType.USER.equals(type)) {
			throw new WrongNodeTypeException("Not a user node.");
		}
	}
	
}