package ch.hsr.bieridee.models;

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
}