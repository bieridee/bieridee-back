package ch.hsr.bieridee.domain;

/**
 * Class representing a Recommendation.
 */
public class Recommendation {
	private double weight;
	private double normalizedWeight;
	private User user;
	private Beer beer;

	/**
	 * @param weight
	 *            absolute relevance of the recommendation
	 * @param normalizedWeight
	 *            normalized relevance of the recommendeation
	 * @param user
	 *            The <code>User</code> to which the recommendation matches.
	 * @param beer
	 *            The <code>Beer</code> that is recommended.
	 */
	public Recommendation(double weight, double normalizedWeight, User user, Beer beer) {
		this.weight = weight;
		this.normalizedWeight = normalizedWeight;
		this.user = user;
		this.beer = beer;
	}

	public double getWeight() {
		return this.weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getNormalizedWeight() {
		return this.normalizedWeight;
	}

	public void setNormalizedWeight(double normalizedWeight) {
		this.normalizedWeight = normalizedWeight;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Beer getBeer() {
		return this.beer;
	}

	public void setBeer(Beer beer) {
		this.beer = beer;
	}

	@Override
	public String toString() {
		return "Recommendation for User: " + this.user + ". Try " + this.beer;
	}
}
