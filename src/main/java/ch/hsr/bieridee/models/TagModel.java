package ch.hsr.bieridee.models;

import java.util.LinkedList;
import java.util.List;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;

import ch.hsr.bieridee.config.NodeProperty;
import ch.hsr.bieridee.config.NodeType;
import ch.hsr.bieridee.domain.Tag;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.utils.DBUtil;
import ch.hsr.bieridee.utils.NodeUtil;

/**
 * Model to work with and persist the tag obejct.
 * 
 */
public class TagModel extends AbstractModel {

	private Tag domainObject;
	private Node node;

	/**
	 * Creates a TagModel form a node id.
	 * 
	 * @param id
	 *            The id of the desired tag
	 * @throws WrongNodeTypeException
	 *             Thrown if the node with the given name is not of type tag
	 * @throws NotFoundException
	 *             Thrown if the node with the given name is not existing
	 */
	public TagModel(long id) throws WrongNodeTypeException, NotFoundException {
		this(DBUtil.getNodeById(id));
	}

	/**
	 * Creates a TagModel from a tag node.
	 * 
	 * @param tagNode
	 *            The tag node
	 * @throws WrongNodeTypeException
	 *             Thrown if the given node is not of type tag
	 * @throws NotFoundException
	 *             Thrown if the given node has not been found
	 */
	public TagModel(Node tagNode) throws WrongNodeTypeException, NotFoundException {
		NodeUtil.checkNode(tagNode, NodeType.TAG);

		this.node = tagNode;
		final long id = this.node.getId();
		final String name = (String) this.node.getProperty(NodeProperty.Tag.NAME);

		this.domainObject = new Tag(id, name);
	}

	/**
	 * Creates a tag model from a given nave. Creates a new Node in the DB.
	 * 
	 * @param name
	 *            Name of the new tag
	 */
	private TagModel(String name) {
		this.node = DBUtil.createNode(NodeType.TAG);
		this.domainObject = new Tag(this.node.getId(), name);
		this.setName(name);
	}

	public long getId() {
		return this.domainObject.getId();
	}

	public String getName() {
		return this.domainObject.getName();
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setName(String name) {
		this.domainObject.setName(name);
		DBUtil.setProperty(this.node, NodeProperty.Tag.NAME, name);
	}

	public Tag getDomainObject() {
		return this.domainObject;
	}

	public Node getNode() {
		return this.node;
	}

	@Override
	public int hashCode() {
		return this.node.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof TagModel)) {
			return false;
		}
		final TagModel tm = (TagModel) o;
		return this.node.getId() == tm.node.getId();
	}

	/**
	 * Gets a list of all tags as <code>TagModels</code>.
	 * 
	 * @return The list of TagModels
	 * @throws NotFoundException
	 *             Thrown if a node is not existant
	 * @throws WrongNodeTypeException
	 *             Thrown if a node is not of the desired type
	 */
	public static List<TagModel> getAll() throws NotFoundException, WrongNodeTypeException {
		final List<Node> tagNodes = DBUtil.getTagNodeList();
		return createModelsFromNodes(tagNodes);
	}

	private static List<TagModel> createModelsFromNodes(final List<Node> tagNodes) throws WrongNodeTypeException {
		final List<TagModel> tagModelList = new LinkedList<TagModel>();
		for (Node n : tagNodes) {
			tagModelList.add(new TagModel(n));
		}
		return tagModelList;
	}

	/**
	 * Creates an new Tag.
	 * 
	 * @param name
	 *            Name of the new Tag
	 * @return TagModel representing the new Tag
	 */
	public static TagModel create(String name) {
		return new TagModel(name);
	}

	public static TagModel getByName(String name) throws NotFoundException, WrongNodeTypeException {
		Node tagNode = DBUtil.getTagByName(name);
		return new TagModel(tagNode);
	}

}
