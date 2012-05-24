package ch.hsr.bieridee.models;

import java.util.LinkedList;
import java.util.List;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;

import ch.hsr.bieridee.config.NodeProperty;
import ch.hsr.bieridee.config.NodeType;
import ch.hsr.bieridee.domain.Beertype;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.utils.DBUtil;
import ch.hsr.bieridee.utils.NodeUtil;

/**
 * Model to work and persist the beertype object.
 */
public class BeertypeModel extends AbstractModel {

	private Beertype domainObject;
	private Node node;

	/**
	 * Creates a <code>BeertypeModel</code>.
	 * 
	 * @param beertypeId
	 *            The id of the desired beertype
	 * @throws WrongNodeTypeException
	 *             Thrown when the given id does not reference a beertype node
	 * @throws NotFoundException
	 *             Thrown when the given id does not reference an existing node
	 */
	public BeertypeModel(long beertypeId) throws WrongNodeTypeException, NotFoundException {
		this(DBUtil.getNodeById(beertypeId));
	}

	/**
	 * Creates a <code>BeertypeModel</code>.
	 * 
	 * @param node
	 *            the node containing <code>Beertype</code> properties.
	 * @throws WrongNodeTypeException
	 *             Thrown when the given node is not of type beertype
	 * @throws NotFoundException
	 *             Thrown if the node does not exist
	 */
	public BeertypeModel(Node node) throws WrongNodeTypeException, NotFoundException {

		NodeUtil.checkNode(node, NodeType.BEERTYPE);

		this.node = node;

		final long id = this.node.getId();
		final String name = (String) this.node.getProperty(NodeProperty.Beertype.NAME);
		final String description = (String) this.node.getProperty(NodeProperty.Beertype.DESCRIPTION);
		this.domainObject = new Beertype(id, name, description);
	}

	public Beertype getDomainObject() {
		return this.domainObject;
	}

	public Node getNode() {
		return this.node;
	}

	public String getDescription() {
		return this.domainObject.getDescription();
	}

	public String getName() {
		return this.domainObject.getName();
	}

	public long getId() {
		return this.domainObject.getId();
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setId(long id) {
		this.domainObject.setId(id);
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setName(String name) {
		this.domainObject.setName(name);
		this.node.setProperty(NodeProperty.Beertype.NAME, name);
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setDescription(String description) {
		this.domainObject.setDescription(description);
		this.node.setProperty(NodeProperty.Beertype.DESCRIPTION, description);
	}

	/**
	 * Gets a list of all beertypes as <code>beertypeModel</code>.
	 * 
	 * @return The beertyeModel list
	 * @throws NotFoundException
	 *             Thrown if a node is not existant.
	 * @throws WrongNodeTypeException
	 *             Thrown if a node is not of the desired type
	 */
	public static List<BeertypeModel> getAll() throws NotFoundException, WrongNodeTypeException {
		final List<Node> beerTypeNodes = DBUtil.getBeertypeNodeList();
		final List<BeertypeModel> beertypeModels = new LinkedList<BeertypeModel>();
		for (Node n : beerTypeNodes) {
			beertypeModels.add(new BeertypeModel(n));
		}
		return beertypeModels;
	}

	@Override
	public int hashCode() {
		return new Long(this.node.getId()).hashCode();
	}
}
