package ch.hsr.bieridee.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;

import ch.hsr.bieridee.config.NodeProperty;
import ch.hsr.bieridee.config.NodeType;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.AbstractActionModel;
import ch.hsr.bieridee.models.TimelineModel;
import ch.hsr.bieridee.utils.DBUtil;

/**
 * Tests Timeline functionality.
 * 
 */
public class TimelineTest extends DBTest {
	/**
	 * Tests the chronological timeline "chaining".
	 */

	@Test
	public void testTimelineOrder() {

		final List<Node> actions = DBUtil.getTimeLine(0,0);
		long previous = Long.MAX_VALUE;
		for (Node n : actions) {
			final Long timestamp = (Long) n.getProperty(NodeProperty.Action.TIMESTAMP);
			Assert.assertTrue(timestamp <= previous);
			previous = timestamp;
		}
	}

	/**
	 * Tests the limit parameter.
	 */
	private List<Node> testTimelineSize(int size) {
		final int limit = 3;
		return DBUtil.getTimeLine(limit,0);
	}

	/**
	 * 
	 */
	@Test
	public void testTimelineNegativeSize() {
		final int limit = -1;
		final List<Node> nodes = this.testTimelineSize(limit);
		Assert.assertTrue(0 != nodes.size());

	}

	/**
	 * 
	 */
	@Test
	public void testTimelineSize() {
		final int limit = 3;
		final List<Node> nodes = this.testTimelineSize(limit);
		Assert.assertEquals(limit, nodes.size());
	}

	/**
	 * Tests whether an newly inserted Action is at the first position in the Timeline-Tree.
	 */
	@Test
	public void testTimelineAddAction() {
		final Node consumption = DBUtil.createNode(NodeType.CONSUMPTION);
		final Long time = System.currentTimeMillis();
		DBUtil.setProperty(consumption, NodeProperty.Action.TIMESTAMP, time);
		DBUtil.addToTimeLine(consumption);
		final List<Node> actions = DBUtil.getTimeLine(1,0);
		final Node first = actions.get(0);
		final Long savedTime = (Long) first.getProperty(NodeProperty.Action.TIMESTAMP);
		Assert.assertEquals(time, savedTime);
	}

	/**
	 * Tests the Timeline User filter functionality.
	 */
	@Test
	public void testTimelineUserFilter() {
		final String username = "jonas";
		try {
			for (AbstractActionModel actionModel : TimelineModel.getAllForUser(username)) {
				Assert.assertEquals(username, actionModel.getUser().getUsername());
			}
		} catch (NotFoundException e) {
			Assert.fail();
			e.printStackTrace();
		} catch (WrongNodeTypeException e) {
			Assert.fail();
			e.printStackTrace();
		}

		final int limit = 2;
		try {
			Assert.assertEquals(limit, TimelineModel.getAllForUser(username, limit).size());
		} catch (NotFoundException e) {
			Assert.fail();
			e.printStackTrace();
		} catch (WrongNodeTypeException e) {
			Assert.fail();
			e.printStackTrace();
		}

	}
}
