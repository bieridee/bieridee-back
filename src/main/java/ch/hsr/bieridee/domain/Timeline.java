package ch.hsr.bieridee.domain;

import java.util.LinkedList;
import java.util.List;

/**
 * @author chrigi Class representing a Timeline.
 */
public class Timeline {
	private List<Action> actions;

	/**
	 * @param actions
	 *            List with existing Actions.
	 */
	public Timeline(List<Action> actions) {
		this.actions = actions;
	}

	/**
	 * Constructor creating an empty List of actions.
	 */
	public Timeline() {
		this.actions = new LinkedList<Action>();
	}

	/**
	 * @param activity
	 *            add an <code>Activity</code> to the Timeline.
	 */
	public void addActivity(Action activity) {
		this.getActivities().add(activity);
	}

	/**
	 * @param activity
	 *            remove an <code>Activity</code> from the Timeline.
	 */
	public void removeActivity(Action activity) {
		this.getActivities().remove(activity);
	}

	public List<Action> getActivities() {
		return this.actions;
	}

	public void setActivities(List<Action> activities) {
		this.actions = activities;
	}

}
