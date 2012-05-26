package ch.hsr.bieridee.models;

import java.util.Date;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;
import org.neo4j.graphdb.Relationship;

import ch.hsr.bieridee.config.NodeProperty;
import ch.hsr.bieridee.config.NodeType;
import ch.hsr.bieridee.config.RelType;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.utils.DBUtil;
import ch.hsr.bieridee.utils.NodeUtil;

/**
 * Abstract class for action models (consumtion and rating).
 * 
 */
public abstract class AbstractActionModel extends AbstractModel {

	protected String type;
	protected Date date;
	protected BeerModel beerModel;
	protected UserModel userModel;

	/**
	 * Basic constructor of the ActionModel.
	 * 
	 * @param type
	 *            Type of the action, consumtion or rating
	 * @param beerModel
	 *            The connected BeerModel
	 * @param userModel
	 *            The connected userModel
	 */
	protected AbstractActionModel(String type, BeerModel beerModel, UserModel userModel) {
		this.beerModel = beerModel;
		this.userModel = userModel;
		this.date = new Date();
		this.type = type;
		this.node = DBUtil.createNode(type);
		DBUtil.createRelationship(this.node, RelType.CONTAINS, beerModel.getNode());
		DBUtil.createRelationship(userModel.getNode(), RelType.DOES, this.node);
		if(type == NodeType.RATING) {
			DBUtil.addToRatingIndex(this.node, beerModel, userModel);
		}
	}

	/**
	 * Basic constructor of the ActionModel from a node.
	 * 
	 * @param type
	 *            Type of the node
	 * @param node
	 *            The action node, consumtion or rating
	 * @throws NotFoundException
	 *             Thrown if the given node can not be found
	 * @throws WrongNodeTypeException
	 *             Thrown if the given node is not of type user
	 */
	public AbstractActionModel(String type, Node node) throws NotFoundException, WrongNodeTypeException {
		NodeUtil.checkNode(node, type);
		this.node = node;
		final long timeStamp = (Long) this.node.getProperty(NodeProperty.Rating.TIMESTAMP, 0L);

		final Relationship beerRel = this.node.getSingleRelationship(RelType.CONTAINS, Direction.OUTGOING);
		final Node beerNode = beerRel.getEndNode();
		this.beerModel = new BeerModel(beerNode);

		final Relationship userRel = this.node.getSingleRelationship(RelType.DOES, Direction.INCOMING);
		final Node userNode = userRel.getStartNode();
		this.userModel = new UserModel(userNode);

		this.date = new Date(timeStamp);
		this.type = (String) this.node.getProperty(NodeProperty.TYPE);
	}

	public String getType() {
		return this.type;
	}

	public Date getDate() {
		return this.date;
	}

	public BeerModel getBeer() {
		return this.beerModel;
	}

	public UserModel getUser() {
		return this.userModel;
	}

	/**
	 * @return number of seconds ago the action was created.
	 */
	public long getSecondsAgo() {
		final long timestampNow = System.currentTimeMillis();
		final long timestampCreated = this.getDate().getTime();
		return (timestampNow - timestampCreated) / 1000;
	}

}
