package ch.hsr.bieridee.models;

import java.util.Date;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;
import org.neo4j.graphdb.Relationship;

import ch.hsr.bieridee.config.NodeType;
import ch.hsr.bieridee.config.RelType;
import ch.hsr.bieridee.domain.Consumption;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.utils.DBUtil;
import ch.hsr.bieridee.utils.NodeProperty;
import ch.hsr.bieridee.utils.NodeUtil;

/**
 * Model to work with and persist the User object.
 */
public class ConsumptionModel extends AbstractModel {

	private Consumption domainObject;
	private Node node;

	/**
	 * Creates an empty RatingModel, needed to create a new Rating.
	 * 
	 */
	private ConsumptionModel(BeerModel beerModel, UserModel userModel) {
		this.domainObject = new Consumption(new Date(System.currentTimeMillis()), beerModel.getDomainObject(), userModel.getDomainObject());
		this.node = DBUtil.createNode(NodeType.CONSUMPTION);
		DBUtil.createRelationship(this.node, RelType.CONTAINS, beerModel.getNode());
		DBUtil.createRelationship(userModel.getNode(), RelType.DOES, this.node);
		this.setDate(this.domainObject.getDate());
	}

	/**
	 * Creates a UserModel, consisting from a User domain object and the corresponding Node.
	 * 
	 * @param node
	 *            Ratingnode
	 * @throws NotFoundException
	 *             Thrown if the given node can not been found
	 * @throws WrongNodeTypeException
	 *             Thrown if the given node is not of type user
	 */
	public ConsumptionModel(Node node) throws NotFoundException, WrongNodeTypeException {
		NodeUtil.checkNode(node, NodeType.CONSUMPTION);

		this.node = node;
		final long timeStamp = (Long) this.node.getProperty(NodeProperty.Action.TIMESTAMP);

		final Relationship beerRel = this.node.getSingleRelationship(RelType.CONTAINS, Direction.OUTGOING);
		final Node beerNode = beerRel.getEndNode();
		final BeerModel beerModel = new BeerModel(beerNode);

		final Relationship userRel = this.node.getSingleRelationship(RelType.DOES, Direction.INCOMING);
		final Node userNode = userRel.getStartNode();
		final UserModel userModel = new UserModel(userNode);

		final Date d = new Date(timeStamp);
		this.domainObject = new Consumption(d, beerModel.getDomainObject(), userModel.getDomainObject());

	}

	public Consumption getDomainObject() {
		return this.domainObject;
	}

	public Node getNode() {
		return this.node;
	}

	public Date getDate() {
		return this.domainObject.getDate();
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setDate(Date d) {
		this.domainObject.setDate(d);
		DBUtil.setProperty(this.node, NodeProperty.Rating.TIMESTAMP, d.getTime());
	}

	/**
	 * Creates a new user and returns a new UserModel for it.
	 * 
	 * @param beerModel
	 *            The BeerModel.
	 * @param userModel
	 *            The UserModel.
	 * @return The UserModel containing the new user node and the user domain object
	 */
	public static ConsumptionModel create(BeerModel beerModel, UserModel userModel) {
		return new ConsumptionModel(beerModel, userModel);
	}

}
