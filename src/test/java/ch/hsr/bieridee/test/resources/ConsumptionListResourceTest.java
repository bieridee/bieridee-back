package ch.hsr.bieridee.test.resources;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.mortbay.log.Log;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;

import ch.hsr.bieridee.config.NodeProperty;
import ch.hsr.bieridee.config.NodeType;
import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BeerModel;
import ch.hsr.bieridee.models.ConsumptionModel;
import ch.hsr.bieridee.models.UserModel;
import ch.hsr.bieridee.utils.Cypher;
import ch.hsr.bieridee.utils.Cypherqueries;
import ch.hsr.bieridee.utils.DBUtil;

/**
 * Tests for the beer consumptions.
 * 
 */
public class ConsumptionListResourceTest extends ResourceTest {

	/**
	 * Tests the creation of a consumption.
	 */
	@Test
	public void createConsumption() {
		final String uri = Res.PUBLIC_API_URL + "/beers/30/consumptions/alki";
		this.postJson(uri, new JSONObject());

		final List<Node> consumptionNodes = DBUtil.getTimeLine(1,0);
		final Node consumptionNode = consumptionNodes.get(0);
		Assert.assertEquals(NodeType.CONSUMPTION, consumptionNode.getProperty(NodeProperty.TYPE));
		try {
			final Node beerNode = DBUtil.getNodeById(30);
			final Node userNode = DBUtil.getUserByName("alki");
			final BeerModel bm = new BeerModel(beerNode);
			final UserModel um = new UserModel(userNode);

			final ConsumptionModel cm = new ConsumptionModel(consumptionNode);
			Assert.assertEquals(bm.getDomainObject().getName(), cm.getDomainObject().getBeer().getName());
			Assert.assertEquals(um.getUsername(), cm.getDomainObject().getUser().getUsername());
		} catch (NotFoundException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (WrongNodeTypeException e) {
			Assert.fail();
			e.printStackTrace();
		}
	}

	/**
	 * Tests if the order of consumptions is chronological and if the cypherquery only returns consumption nodes.
	 */
	@Test
	public void getChronologicalConsumptions() {
		final List<Node> consumptions = Cypher.executeAndGetNodes(Cypherqueries.GET_ALL_CONSUMPTIONS, "Action");
		long time = Long.MAX_VALUE;
		for (Node n : consumptions) {
			try {
				Assert.assertEquals(n.getProperty("type"), NodeProperty.Consumption.TYPE);
				final Long thisTime = (Long) n.getProperty("timestamp");
				Assert.assertTrue(thisTime < time);
				time = thisTime;
			} catch (NotFoundException e) {
				e.printStackTrace();
				Assert.fail();
			}
		}
	}

	/**
	 * Tests the retrieval of all consumptions of a beer.
	 */
	@Test
	public void getBeerConsumptions() throws JSONException {
		final int beerId = 28;
		final String uri = Res.PUBLIC_API_URL + "/beers/" + beerId + "/consumptions";
		final JSONArray consumptionsJSON = getJSONArray(uri);
		try {
			final JSONObject cons1 = (JSONObject) consumptionsJSON.get(0);
			final JSONObject cons2 = (JSONObject) consumptionsJSON.get(1);
			final JSONObject beer1 = (JSONObject) cons1.get("beer");
			final JSONObject beer2 = (JSONObject) cons2.get("beer");

			Assert.assertEquals("consumption", cons1.get("type"));
			Assert.assertEquals("consumption", cons2.get("type"));
			Assert.assertEquals(Res.PUBLIC_API_URL + "/beers/" + beerId, beer1.get("uri"));
			Assert.assertEquals(Res.PUBLIC_API_URL + "/beers/" + beerId, beer2.get("uri"));
		} catch (JSONException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	/**
	 * Tests the beer consumptions of a specific beer for specific user.
	 */
	@Test
	public void getUserBeerConsumptions() {
		final String uri = Res.PUBLIC_API_URL + "/beers/28/consumptions/trinker";
		final JSONArray consumptionsJSON = getJSONArray(uri);
		try {
			final JSONObject cons1 = (JSONObject) consumptionsJSON.get(0);
			final JSONObject user1 = (JSONObject) cons1.get("user");

			Assert.assertEquals("consumption", cons1.get("type"));
			Assert.assertEquals("trinker", user1.get("user"));
		} catch (JSONException e) {
			Assert.fail();
			e.printStackTrace();
		}
	}
}
