package ch.hsr.bieridee.domain;

import java.util.Date;

import ch.hsr.bieridee.domain.interfaces.IDomain;

/**
 * @author chrigi
 * 
 */
public class Rating extends Action implements IDomain{

	private int value;

	/**
	 * @param date
	 *            Date the Activity happened.
	 * @param beer
	 *            The <code>Beer</code> associated with the activity.
	 * @param user
	 *            The <code>User</code> created that created the activity
	 * @param value
	 *            Rated value of the Beer.
	 */
	public Rating(Date date, Beer beer, User user, int value) {
		super(date, beer, user);
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Rating value: " + this.value + ", Date: " + this.getDate()
				+ ", User: " + this.getUser();
	}

}
