package ch.hsr.bieridee.models;

import java.util.Date;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;

import ch.hsr.bieridee.config.NodeType;
import ch.hsr.bieridee.domain.Consumption;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.utils.DBUtil;
import ch.hsr.bieridee.utils.NodeProperty;

/**
 * Model to work with and persist the User object.
 */
public class ConsumptionModel extends AbstractActionModel {

	private Consumption domainObject;

	/**
	 * Creates an empty RatingModel, needed to create a new Rating.
	 * 
	 */
	private ConsumptionModel(BeerModel beerModel, UserModel userModel) {
		super(NodeType.CONSUMPTION, beerModel, userModel);
		this.domainObject = new Consumption(new Date(System.currentTimeMillis()), beerModel.getDomainObject(), userModel.getDomainObject());
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
		super(NodeType.CONSUMPTION, node);
		this.domainObject = new Consumption(this.date, this.beerModel.getDomainObject(), this.userModel.getDomainObject());
	}

	public Consumption getDomainObject() {
		return this.domainObject;
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

	@Override
	public String toString() {
		return this.domainObject.toString();
	}

}
