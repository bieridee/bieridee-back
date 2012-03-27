package ch.hsr.bieridee.domain;

import java.util.Date;

/**
 * @author chrigi Class representing an Activity
 * 
 */
public abstract class Activity {

	private Date date;

	/**
	 * @param date
	 *            Date the Activity happened.
	 * @param beer
	 *            The <code>Beer</code> associated with the activity.
	 * @param user
	 *            The <code>User</code> created that created the activity
	 */
	public Activity(Date date, Beer beer, User user) {
		super();
		this.date = date;
		this.beer = beer;
		this.user = user;
	}

	private Beer beer;
	private User user;

	Beer getBeer() {
		return this.beer;
	}

	void setBeer(Beer newBeer) {
		this.beer = newBeer;
	}

	User getUser() {
		return this.user;
	}

	void setUser(User newUser) {
		this.user = newUser;
	}

	private Date getDate() {
		return this.date;
	}

	private void setDate(Date date) {
		this.date = date;
	}

}
