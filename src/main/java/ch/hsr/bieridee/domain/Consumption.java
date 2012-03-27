package ch.hsr.bieridee.domain;

import java.util.Date;

/**
 * @author chrigi Class representing a Consumption.
 */
public class Consumption extends Activity {

	/**
	 * @param date
	 *            Date the Activity happened.
	 * @param beer
	 *            The <code>Beer</code> associated with the activity.
	 * @param user
	 *            The <code>User</code> created that created the activity
	 */
	
	public Consumption(Date date, Beer beer, User user) {
		super(date, beer, user);
	}

	@Override
	public String toString() {
		return this.getUser() + " consumes " + this.getBeer();
	}

}
