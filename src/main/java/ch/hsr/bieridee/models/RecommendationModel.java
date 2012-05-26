package ch.hsr.bieridee.models;

import java.text.DecimalFormat;

import org.neo4j.graphdb.NotFoundException;

import ch.hsr.bieridee.domain.Recommendation;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;

/**
 * RecommendationModel. This model contains no node, as it is completly calculated it has no need to be persisted in the
 * database.
 * 
 */
public class RecommendationModel extends AbstractModel implements Comparable<RecommendationModel> {

	private Recommendation recommendation;

	/**
	 * Creates a new RecommendationModel.
	 * 
	 * @param weight
	 *            Relevance of the recommendation
	 * @param normalizedWeight
	 *            Normalized relevance of the recommendation
	 * @param forUser
	 *            The user to geht the recommendation
	 * @param beer
	 *            The recommended beer
	 */
	public RecommendationModel(double weight, double normalizedWeight, UserModel forUser, BeerModel beer) {
		this.recommendation = new Recommendation(weight, normalizedWeight, forUser.getDomainObject(), beer.getDomainObject());
	}

	public double getWeight() {
		return this.recommendation.getWeight();
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setWeight(double weight) {
		this.recommendation.setWeight(weight);
	}

	public double getNormalizedWeight() {
		return this.recommendation.getNormalizedWeight();
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setNormalizedWeight(double weight) {
		this.recommendation.setNormalizedWeight(weight);
	}
	
	// SUPPRESS CHECKSTYLE: getter
	public double getRoundedNormalizdWeight() {
		final DecimalFormat df = new DecimalFormat("0.0");
		final String doubleString = df.format(this.getNormalizedWeight());
		return new Double(doubleString);
	}

	// SUPPRESS CHECKSTYLE: getter
	public UserModel getUser() throws NotFoundException, WrongNodeTypeException {
		return new UserModel(this.recommendation.getUser().getUsername());
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setUser(UserModel user) {
		this.recommendation.setUser(user.getDomainObject());
	}

	// SUPPRESS CHECKSTYLE: getter
	public BeerModel getBeer() throws NotFoundException, WrongNodeTypeException {
		return new BeerModel(this.recommendation.getBeer().getId());
	}

	// SUPPRESS CHECKSTYLE: setter
	public void setBeer(BeerModel beer) {
		this.recommendation.setBeer(beer.getDomainObject());
	}

	@Override
	public int compareTo(RecommendationModel o) {
		final Double w1 = this.getWeight();
		final Double w2 = o.getWeight();
		return w2.compareTo(w1);
	}

}
