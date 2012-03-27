package ch.hsr.bieridee.domain;

import java.util.List;

/**
 * @author chrigi Class representing a Timeline.
 */
public class Timeline {
	private List<Actions> activities;

	/**
	 * @param activity
	 *            add an <code>Activity</code> to the Timeline.
	 */
	public void addActivity(Actions activity) {
		this.getActivities().add(activity);
	}

	/**
	 * @param activity
	 *            remove an <code>Activity</code> from the Timeline.
	 */
	public void removeActivity(Actions activity) {
		this.getActivities().remove(activity);
	}

	List<Actions> getActivities() {
		return this.activities;
	}

	void setActivities(List<Actions> activities) {
		this.activities = activities;
	}

}
