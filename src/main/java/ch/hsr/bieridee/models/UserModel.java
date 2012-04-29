package ch.hsr.bieridee.models;

import java.util.LinkedList;
import java.util.List;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;

import ch.hsr.bieridee.config.NodeType;
import ch.hsr.bieridee.domain.User;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.utils.DBUtil;
import ch.hsr.bieridee.utils.NodeProperty;
import ch.hsr.bieridee.utils.NodeUtil;

/**
 * Model to work with and persist the User object.
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
	 * Creates an empty UserModel, needed to create a new User.
	 * 
	 */
	private UserModel(User user) {
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
		final String username = (String) this.node.getProperty(NodeProperty.User.USERNAME);
		final String prename = (String) this.node.getProperty(NodeProperty.User.PRENAME);
		final String surname = (String) this.node.getProperty(NodeProperty.User.SURNAME);
		final String password = (String) this.node.getProperty(NodeProperty.User.PASSWORD);
		final String email = (String) this.node.getProperty(NodeProperty.User.EMAIL);

		this.domainObject = new User(username, password, prename, surname, email);

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
		DBUtil.setProperty(this.node, NodeProperty.User.USERNAME, username);
	}

	public String getPrename() {
		return this.domainObject.getPrename();
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setPrename(String prename) {
		this.domainObject.setPrename(prename);
		DBUtil.setProperty(this.node, NodeProperty.User.PRENAME, prename);
	}

	public String getSurname() {
		return this.domainObject.getSurname();
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setSurname(String surname) {
		this.domainObject.setSurname(surname);
		DBUtil.setProperty(this.node, NodeProperty.User.SURNAME, surname);
	}

	public String getPassword() {
		return this.domainObject.getPassword();
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setPassword(String password) {
		this.domainObject.setPassword(password);
		DBUtil.setProperty(this.node, NodeProperty.User.PASSWORD, password);
	}

	public String getEmail() {
		return this.domainObject.getEmail();
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setEmail(String email) {
		this.domainObject.setEmail(email);
		DBUtil.setProperty(this.node, NodeProperty.User.EMAIL, email);
	}

	/**
	 * Creates a new user and returns a new UserModel for it.
	 * 
	 * @param username
	 *            Name of the new user
	 * @param password
	 *            Password for the new user
	 * @param prename
	 *            Prename of the new user
	 * @param surname
	 *            Surname of the new user
	 * @param email
	 *            Email address of the new user
	 * @return The UserModel containing the new user node and the user domain object
	 */
	public static UserModel create(String username, String password, String prename, String surname, String email) {
		return new UserModel(new User(username, password, prename, surname, email));
	}

	/**
	 * Gets a list of all users as <code>UserModel</code>s.
	 * 
	 * @return List of <code>UserModel</code>
	 * @throws NotFoundException
	 *             Thrown if a node is not existant.
	 * @throws WrongNodeTypeException
	 *             Thrown if a node is not of the desired type
	 */
	public static List<UserModel> getAll() throws NotFoundException, WrongNodeTypeException {
		final List<Node> userNodes = DBUtil.getUserNodeList();
		final List<UserModel> userModels = new LinkedList<UserModel>();
		for (Node n : userNodes) {
			userModels.add(new UserModel(n));
		}
		return userModels;
	}

}
