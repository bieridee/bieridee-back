package ch.hsr.bieridee.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.neo4j.graphdb.Node;

import ch.hsr.bieridee.utils.DBUtil;
import ch.hsr.bieridee.utils.NodeProperty;

/**
 * Tests Timeline functionality.
 * 
 */
public class TimelineTest {
	/**
	 * Tests the chronological timeline "chaining".
	 */

	@Test
	public void testTimeLineOrder() {

		final List<Node> actions = DBUtil.getTimeLine(0);
		long previous = Long.MAX_VALUE;
		for (Node n : actions) {
			final Long timestamp = (Long) n.getProperty(NodeProperty.Action.TIMESTAMP);
			System.out.println(timestamp);
			Assert.assertTrue(timestamp <= previous);
			previous = timestamp;
		}
	}

	/**
	 * Tests the limit parameter.
	 */
	@Test
	public void testTimeLineSize() {
		final int limit = 3;
		final List<Node> actions = DBUtil.getTimeLine(limit);
		Assert.assertEquals(limit, actions.size());
	}

}
