package ch.hsr.bieridee.domain;

import java.util.Date;

/**
 * @author chrigi
 * 
 */
public class Rating extends Activity {

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

	int getValue() {
		return value;
	}

	void setValue(int value) {
		this.value = value;
	}

}
