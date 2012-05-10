package ch.hsr.bieridee.models;

import java.util.LinkedList;
import java.util.List;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;

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
		this.domainObject = new Brewery(id, name, size, description, picture);
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

	// SUPPRESS CHECKSTYLE: setter
	public void setId(long id) {
		this.domainObject.setId(id);
	}

	public String getName() {
		return this.domainObject.getName();
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setName(String name) {
		this.domainObject.setName(name);
		this.node.setProperty("name", name);
	}

	public String getSize() {
		return this.domainObject.getSize();
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setSize(String size) {
		this.domainObject.setSize(size);
		this.node.setProperty("size", size);
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

	private static List<BreweryModel> createModelsFromNodes(List<Node> breweryNodes) throws NotFoundException, WrongNodeTypeException {
		final List<BreweryModel> models = new LinkedList<BreweryModel>();
		for (Node n : breweryNodes) {
			models.add(new BreweryModel(n));
		}
		return models;
	}

	public String getDescription() {
		return this.domainObject.getDescription();
	}

	public String getPicture() {
		return this.domainObject.getPicture();
	}

}
