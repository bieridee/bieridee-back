package ch.hsr.bieridee.models;

import java.util.LinkedList;
import java.util.List;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;

import ch.hsr.bieridee.config.NodeProperty;
import ch.hsr.bieridee.config.NodeType;
import ch.hsr.bieridee.domain.Brewery;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.utils.DBUtil;
import ch.hsr.bieridee.utils.NodeUtil;

/**
 * Model to work and persist the brewery object.
 */
public class BreweryModel extends AbstractModel {

	private Brewery domainObject;
	private Node node;

	/**
	 * Creates a <code>BreweryModel</code> by id.
	 * 
	 * @param breweryId
	 *            The id of the desired brewery
	 * @throws WrongNodeTypeException
	 *             Thrown when the given id does not reference a brewery node
	 * @throws NotFoundException
	 *             Thrown when the given id does not reference an existing node
	 */
	public BreweryModel(long breweryId) throws WrongNodeTypeException, NotFoundException {
		this(DBUtil.getNodeById(breweryId));
	}

	/**
	 * Creates a <code>BreweryModel</code> by Node.
	 * 
	 * @param node
	 *            the node containing <code>Brewery</code> properties.
	 * @throws WrongNodeTypeException
	 *             Thrown when the given node is not of type beertype
	 * @throws NotFoundException
	 *             Thrown when the given id does not reference an existing node
	 */
	public BreweryModel(Node node) throws WrongNodeTypeException, NotFoundException {
		NodeUtil.checkNode(node, NodeType.BREWERY);

		this.node = node;
		final long id = this.node.getId();
		final String name = (String) this.node.getProperty("name");
		final String size = (String) this.node.getProperty("size");
		final String description = (String) this.node.getProperty("description");
		final String picture = (String) this.node.getProperty("picture");
		this.domainObject = new Brewery(name, size, description, picture);
		this.setId(id);

	}

	private BreweryModel(String name, String description, String picture, String size) {
		final Node breweryNode = DBUtil.createNode(NodeType.BREWERY);
		this.node = breweryNode;
		this.domainObject = new Brewery(this.node.getId(), name, size, description, picture);
		this.setName(name);
		this.setSize(size);
		this.setDescription(description);
		this.setPicture(picture);
	}

	public boolean isUnknown() {
		return this.node.hasProperty(NodeProperty.UNKOWNNODE);
	}

	public Brewery getDomainObject() {
		return this.domainObject;
	}

	public Node getNode() {
		return this.node;
	}

	public long getId() {
		return this.domainObject.getId();
	}

	public String getDescription() {
		return this.domainObject.getDescription();
	}

	public String getPicture() {
		return this.domainObject.getPicture();
	}

	public String getName() {
		return this.domainObject.getName();
	}

	public String getSize() {
		return this.domainObject.getSize();
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setId(long id) {
		this.domainObject.setId(id);
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setName(String name) {
		DBUtil.setProperty(this.node, NodeProperty.Brewery.NAME, name);
		this.domainObject.setName(name);
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setSize(String size) {
		DBUtil.setProperty(this.node, NodeProperty.Brewery.SIZE, size);
		this.domainObject.setSize(size);
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setDescription(String description) {
		DBUtil.setProperty(this.node, NodeProperty.Brewery.DESCRIPTION, description);
		this.domainObject.setDescription(description);
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setPicture(String picture) {
		DBUtil.setProperty(this.node, NodeProperty.Brewery.PICTURE, picture);
		this.domainObject.setPicture(picture);
	}

	/**
	 * Gets a list of all breweries as <code>BreweryModels</code>.
	 * 
	 * @return The list of BreweryModels
	 * @throws NotFoundException
	 *             Thrown if a node is not existant
	 * @throws WrongNodeTypeException
	 *             Thrown if a node is not of the desired type
	 */
	public static List<BreweryModel> getAll() throws NotFoundException, WrongNodeTypeException {
		return createModelsFromNodes(DBUtil.getBreweryNodeList());
	}
	
	public static List<BreweryModel> getAll(int items, int skip) throws NotFoundException, WrongNodeTypeException {
		return createModelsFromNodes(DBUtil.getBreweryNodeList(items, skip));
	}

	/**
	 * Gets a list of BeweryModels filtered by brewerySize.
	 * 
	 * @param brewerySize
	 *            The brewerySize
	 * @return The filtered list of BreweryModels
	 * @throws NotFoundException
	 *             Thrown if a node is not existant
	 * @throws WrongNodeTypeException
	 *             Thrown if a node is not of the desired type
	 */
	public static List<BreweryModel> getAll(String brewerySize) throws NotFoundException, WrongNodeTypeException {
		return createModelsFromNodes(DBUtil.getBreweryNodeList(brewerySize));
	}

	public static List<BreweryModel> getAll(String brewerySize, int items, int skip) throws NotFoundException, WrongNodeTypeException {
		return createModelsFromNodes(DBUtil.getBreweryNodeList(brewerySize, items, skip));
	}

	private static List<BreweryModel> createModelsFromNodes(List<Node> breweryNodes) throws NotFoundException, WrongNodeTypeException {
		final List<BreweryModel> models = new LinkedList<BreweryModel>();
		for (Node n : breweryNodes) {
			models.add(new BreweryModel(n));
		}
		return models;
	}

	/**
	 * Creates a new Brewery and saves it persistent into the database.
	 * 
	 * @param name
	 *            name of the brewery.
	 * @param description
	 *            text describing the brewery
	 * @param picture
	 *            path to the picture
	 * @param size
	 *            size of the brewery
	 * @return a <code>BreweryModel</code> representing the created brewery.
	 */
	public static BreweryModel create(String name, String description, String picture, String size) {
		return new BreweryModel(name, description, picture, size);
	}

	/**
	 * Gets the BreweryModel for an unknown brewery.
	 * 
	 * @return The mysterious unknown brewery
	 * @throws NotFoundException
	 *             Thrown if a node is not existant
	 * @throws WrongNodeTypeException
	 *             Thrown if a node is not of the desired type
	 */
	public static BreweryModel getUnknown() throws NotFoundException, WrongNodeTypeException {
		return new BreweryModel(DBUtil.getUnknownNode(NodeType.BREWERY));
	}

}
