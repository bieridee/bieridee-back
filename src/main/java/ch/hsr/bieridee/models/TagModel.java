package ch.hsr.bieridee.models;

import java.util.LinkedList;
import java.util.List;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;

import ch.hsr.bieridee.config.NodeType;
import ch.hsr.bieridee.domain.Tag;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.utils.DBUtil;
import ch.hsr.bieridee.utils.NodeProperty;
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
	 * @param name
	 *            The name of the desired tag
	 * @throws WrongNodeTypeException
	 *             Thrown if the node with the given name is not of type tag
	 * @throws NotFoundException
	 *             Thrown if the node with the given name is not existing
	 */
	public TagModel(String name) throws WrongNodeTypeException, NotFoundException {
		this(DBUtil.getTagByName(name));
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
		final String name = (String) this.node.getProperty(NodeProperty.Tag.NAME);

		this.domainObject = new Tag(name);
	}

	/**
	 * Creates a tag model from a given Tag. Creates a new Node in the DB.
	 * 
	 * @param t
	 *            Tag Object.
	 */
	public TagModel(Tag t) {
		this.domainObject = t;
		this.node = DBUtil.createNode(NodeProperty.Tag.TYPE);
		this.setName(t.getName());
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
		final List<TagModel> tagModelList = new LinkedList<TagModel>();
		for (Node n : tagNodes) {
			tagModelList.add(new TagModel(n));
		}
		return tagModelList;
	}
}
