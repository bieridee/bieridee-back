package ch.hsr.bieridee.domain;

import java.util.Date;

/**
 * @author chrigi Class representing an Activity
 * 
 */
public abstract class Action {

	private Date date;
	private Beer beer;
	private User user;

	/**
	 * @param date
	 *            Date the Activity happened.
	 * @param beer
	 *            The <code>Beer</code> associated with the activity.
	 * @param user
	 *            The <code>User</code> created that created the activity
	 */
	public Action(Date date, Beer beer, User user) {
		this.setDate(date);
		this.setBeer(beer);
		this.setUser(user);
	}


	public Beer getBeer() {
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
