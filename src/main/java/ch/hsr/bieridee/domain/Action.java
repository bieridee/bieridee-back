package ch.hsr.bieridee.domain;

import java.util.Date;

/**
 * @author chrigi Class representing an Activity
 * 
 */
public abstract class Action {

	private Date date;

	/**
	 * @param date
	 *            Date the Activity happened.
	 * @param beer
	 *            The <code>Beer</code> associated with the activity.
	 * @param user
	 *            The <code>User</code> created that created the activity
	 */
	public Action(Date date, Beer beer, User user) {
		this.date = date;
		this.beer = beer;
		this.user = user;
	}

	private Beer beer;
	private User user;

	Beer getBeer() {
		return this.beer;
	}

	public void setBeer(Beer newBeer) {
		this.beer = newBeer;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User newUser) {
		this.user = newUser;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
