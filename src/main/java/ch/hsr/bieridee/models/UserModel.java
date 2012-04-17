package ch.hsr.bieridee.models;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;

import ch.hsr.bieridee.config.NodeType;
import ch.hsr.bieridee.domain.User;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.utils.DBUtil;
import ch.hsr.bieridee.utils.NodeUtil;

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
	 * @param name
	 *            Username of the user
	 * @throws WrongNodeTypeException
	 *             Thrown if the name does not reference a user node
	 * @throws NotFoundException
	 *             Thrown if the id does not reference a node
	 */
	public UserModel(String name) throws WrongNodeTypeException, NotFoundException {
		this(DBUtil.getUserByName(name));
	}

	/**
	 * Creates a UserModel, consisting from a User domain object and the corresponding Node.
	 * 
	 * @param user
	 *            non persistent User Object
	 */
	public UserModel(User user) {
		this.domainObject = user;
		this.node = DBUtil.createNode(NodeType.USER);
		this.setEmail(user.getEmail());
		this.setUsername(user.getUsername());
		this.setPassword(user.getPassword());
		this.setPrename(user.getPrename());
		this.setSurname(user.getSurname());
	}

	/**
	 * Creates a UserModel, consisting from a User domain object and the corresponding Node.
	 * 
	 * @param node
	 *            Usernode
	 * @throws NotFoundException
	 *             Thrown if the given node can not been found
	 * @throws WrongNodeTypeException
	 *             Thrown if the given node is not of type user
	 */
	public UserModel(Node node) throws NotFoundException, WrongNodeTypeException {
		NodeUtil.checkNode(node, NodeType.USER);

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

	// SUPPRESS CHECKSTYLE: setter
	public void setUsername(String username) {
		this.domainObject.setUsername(username);
		DBUtil.setProperty(this.node, "username", username);
	}

	public String getPrename() {
		return this.domainObject.getPrename();
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setPrename(String prename) {
		this.domainObject.setPrename(prename);
		DBUtil.setProperty(this.node, "prename", prename);
	}

	public String getSurname() {
		return this.domainObject.getSurname();
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setSurname(String surname) {
		this.domainObject.setSurname(surname);
		DBUtil.setProperty(this.node, "surname", surname);
	}

	public String getPassword() {
		return this.domainObject.getPassword();
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setPassword(String password) {
		this.domainObject.setSurname(password);
		DBUtil.setProperty(this.node, "password", password);
	}

	public String getEmail() {
		return this.domainObject.getEmail();
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setEmail(String email) {
		this.domainObject.setEmail(email);
		DBUtil.setProperty(this.node, "email", email);
	}
}
