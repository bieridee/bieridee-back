package ch.hsr.bieridee.test;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;

import ch.hsr.bieridee.domain.Consumption;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BeerModel;
import ch.hsr.bieridee.models.ConsumptionModel;
import ch.hsr.bieridee.models.UserModel;
import ch.hsr.bieridee.utils.DBUtil;
import ch.hsr.bieridee.utils.NodeProperty;

/**
 * Tests the consumption model.
 * 
 */
public class ConsumptionModelTest extends DBTest {

	BeerModel bm;

	/**
	 * Creates a beermodel which is used to create a consumption.
	 */
	@Before
	public void setUp() {
		try {
			bm = new BeerModel(30);
		} catch (NotFoundException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (WrongNodeTypeException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	/**
	 * Tests the creation of a new Consumption.
	 */
	@Test
	public void testConsumptionModelCreation() {
		final Node userNode = DBUtil.getUserByName("uese");
		UserModel um = null;
		try {
			um = new UserModel(userNode);
		} catch (NotFoundException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (WrongNodeTypeException e) {
			e.printStackTrace();
			Assert.fail();
		}
		final ConsumptionModel cm = ConsumptionModel.create(this.bm, um);
		final Consumption consumption = cm.getDomainObject();
		Assert.assertEquals(this.bm.getDomainObject(), consumption.getBeer());
		Assert.assertEquals(um.getDomainObject(), consumption.getUser());
	}

	/**
	 * Tests if the Consumption was inserted into the timeline.
	 */
	@Test
	public void testInsertToTimeline() {
		final List<Node> timeLine = DBUtil.getTimeLine(1);
		final Node consumptionNode = timeLine.get(0);
		ConsumptionModel cm = null;
		try {
			cm = new ConsumptionModel(consumptionNode);
		} catch (NotFoundException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (WrongNodeTypeException e) {
			e.printStackTrace();
			Assert.fail();
		}

		Assert.assertEquals(NodeProperty.Consumption.TYPE, cm.getNode().getProperty(NodeProperty.TYPE));
		Assert.assertEquals(cm.getDomainObject().getBeer().getName(), this.bm.getName());
	}

}
