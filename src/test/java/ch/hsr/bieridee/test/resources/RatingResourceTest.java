package ch.hsr.bieridee.test.resources;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;

import ch.hsr.bieridee.config.NodeType;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BeerModel;
import ch.hsr.bieridee.test.helpers.Helpers;
import ch.hsr.bieridee.utils.DBUtil;
import ch.hsr.bieridee.utils.NodeProperty;

/**
 * Tests for the beer rating.
 * 
 */
public class RatingResourceTest extends ResourceTest {
	
	/**
	 * Tests the creation of a rating.
	 */
	@Test
	public void createRating() {
		final JSONObject rating = new JSONObject();
		try {
			rating.put(NodeProperty.Rating.RATING, 4);
		} catch (JSONException e) {
			Assert.fail();
			e.printStackTrace();
		}

		final String uri = Helpers.buildResourceUri("/beers/30/ratings/alki");
		putJSON(uri, rating);
		final List<Node> ratingNodes = DBUtil.getTimeLine(1);
		
		final Node ratingNode = ratingNodes.get(0);
		Assert.assertEquals(NodeType.RATING, ratingNode.getProperty(NodeProperty.TYPE));
		Assert.assertEquals(4, ratingNode.getProperty(NodeProperty.Rating.RATING));
	}
	
	@Test
	public void testAverateRating() {
		final JSONObject rating = new JSONObject();
		try {
			rating.put(NodeProperty.Rating.RATING, 4);
		} catch (JSONException e) {
			Assert.fail();
			e.printStackTrace();
		}

		final String uri = Helpers.buildResourceUri("/beers/28/ratings/saeufer");
		putJSON(uri, rating);
		
		BeerModel beerModel = null;
		try {
			beerModel = new BeerModel(28);
		} catch (NotFoundException e) {
			Assert.fail();
			e.printStackTrace();
		} catch (WrongNodeTypeException e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		Assert.assertNotNull(beerModel);
				Assert.assertEquals(3.0, beerModel.getAverageRating());

	}

	
}
