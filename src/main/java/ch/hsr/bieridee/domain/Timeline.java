package ch.hsr.bieridee.domain;

import java.util.List;

/**
 * @author chrigi Class representing a Timeline.
 */
public class Timeline {
	private List<Activity> activities;

	/**
	 * @param activity
	 *            add an <code>Activity</code> to the Timeline.
	 */
	public void addActivity(Activity activity) {
		this.getActivities().add(activity);
	}

	/**
	 * @param activity
	 *            remove an <code>Activity</code> from the Timeline.
	 */
	public void removeActivity(Activity activity) {
		this.getActivities().remove(activity);
	}

	List<Activity> getActivities() {
		return this.activities;
	}

	void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

}
