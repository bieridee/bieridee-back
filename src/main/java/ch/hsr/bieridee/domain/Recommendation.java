package ch.hsr.bieridee.domain;

/**
 * @author chrigi Class representing a Recommendation.
 */
public class Recommendation {
	private int weight;
	private User user;
	private Beer beer;

	/**
	 * @param weight
	 *            weight describing the probability of matching.
	 * @param user
	 *            The <code>User</code> to which the recommendation matches.
	 * @param beer
	 *            The <code>Beer</code> that is recommended.
	 */
	public Recommendation(int weight, User user, Beer beer) {
		this.weight = weight;
		this.user = user;
		this.beer = beer;
	}

	public int getWeight() {
		return this.weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
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

	void setBeer(Beer beer) {
		this.beer = beer;
	}

	@Override
	public String toString() {
		return "Recommendation from User: " + this.user + ", recommends "
				+ this.beer;
	}
}
