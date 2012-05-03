package ch.hsr.bieridee.models;

import java.util.Date;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;

import ch.hsr.bieridee.config.NodeType;
import ch.hsr.bieridee.domain.Rating;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.utils.DBUtil;
import ch.hsr.bieridee.utils.NodeProperty;

/**
 * Model to work with and persist the User object.
 */
public class RatingModel extends AbstractActionModel {

	private Rating domainObject;

	/**
	 * Creates an empty RatingModel, needed to create a new Rating.
	 * 
	 */
	private RatingModel(int rating, BeerModel beerModel, UserModel userModel) {
		super(NodeType.RATING, beerModel, userModel);
		this.domainObject = new Rating(this.date, this.beerModel.getDomainObject(), this.userModel.getDomainObject(), rating);
		this.setRating(rating);
		this.setDate(this.domainObject.getDate());
	}

	/**
	 * Creates a UserModel, consisting from a User domain object and the corresponding Node.
	 * 
	 * @param node
	 *            Ratingnode
	 * @throws NotFoundException
	 *             Thrown if the given node can not be found
	 * @throws WrongNodeTypeException
	 *             Thrown if the given node is not of type user
	 */
	public RatingModel(Node node) throws NotFoundException, WrongNodeTypeException {
		super(NodeType.RATING, node);

		final int rating = (Integer) this.node.getProperty(NodeProperty.Rating.RATING);
		this.domainObject = new Rating(this.date, this.beerModel.getDomainObject(), this.userModel.getDomainObject(), rating);

	}

	public Rating getDomainObject() {
		return this.domainObject;
	}
	
	// SUPPRESS CHECKSTYLE: setter
	public void setDate(Date d) {
		this.domainObject.setDate(d);
		DBUtil.setProperty(this.node, NodeProperty.Rating.TIMESTAMP, d.getTime());
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setRating(int rating) {
		this.domainObject.setValue(rating);
		DBUtil.setProperty(this.node, NodeProperty.Rating.RATING, rating);
	}

	public int getRating() {
		return this.domainObject.getValue();
	}

	/**
	 * Creates a new user and returns a new UserModel for it.
	 * 
	 * @param value
	 *            The rating value.
	 * @param beerModel
	 *            The BeerModel.
	 * @param userModel
	 *            The UserModel.
	 * @return The UserModel containing the new user node and the user domain object
	 */
	public static RatingModel create(int value, BeerModel beerModel, UserModel userModel) {
		return new RatingModel(value, beerModel, userModel);
	}
	
}
