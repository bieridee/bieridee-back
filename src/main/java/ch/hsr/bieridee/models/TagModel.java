package ch.hsr.bieridee.models;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;

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
	 * @param name The name of the desired tag
	 * @throws WrongNodeTypeException Thrown if the node with the given name is not of type tag
	 * @throws NotFoundException Thrown if the node with the given name is not existing
	 */
	public TagModel(String name) throws WrongNodeTypeException, NotFoundException {
		this(DBUtil.getTagByName(name));
	}
	
	/**
	 * Creates a TagModel from a tag node.
	 * @param tagNode The tag node
	 * @throws WrongNodeTypeException Thrown if the given node is not of type tag
	 * @throws NotFoundException Thrown if the given node has not been found
	 */
	public TagModel(Node tagNode) throws WrongNodeTypeException, NotFoundException {
		NodeUtil.checkNode(tagNode, NodeType.TAG);
		
		this.node = tagNode;
		final String name = (String) this.node.getProperty("name");
		
		this.domainObject = new Tag(name);
	}
	
	public String getName() {
		return this.domainObject.getName();
	}
	
	//SUPPRESS CHECKSTYLE: setter
	public void setName(String name) {
		this.domainObject.setName(name);
		this.node.setProperty("name", name);
	}
	
	public Tag getDomainObject() {
		return this.domainObject;
	}
	
	public Node getNode() {
		return this.node;
	}

}
