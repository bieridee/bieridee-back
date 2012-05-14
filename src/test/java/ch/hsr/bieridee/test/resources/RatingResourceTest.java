package ch.hsr.bieridee.test.resources;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.restlet.representation.Representation;

import ch.hsr.bieridee.config.NodeProperty;
import ch.hsr.bieridee.config.NodeType;
import ch.hsr.bieridee.test.helpers.Helpers;
import ch.hsr.bieridee.utils.DBUtil;

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

		final String uri = Helpers.buildResourceUri("/beers/33/ratings/alki");
		final Representation responseRep = postJson(uri, rating);
		
		try {
			final JSONObject avgRating = new JSONObject(responseRep.getText());
			Assert.assertEquals(4.0, avgRating.getDouble(NodeProperty.Beer.AVERAGE_RATING));
		} catch (IOException e) {
			Assert.fail();
			e.printStackTrace();
		} catch (JSONException e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		final List<Node> ratingNodes = DBUtil.getTimeLine(1,0);
		
		final Node ratingNode = ratingNodes.get(0);
		Assert.assertEquals(NodeType.RATING, ratingNode.getProperty(NodeProperty.TYPE));
		Assert.assertEquals(4, ratingNode.getProperty(NodeProperty.Rating.RATING));
	}
	
	/**
	 * Tests the retrieval of an active rating.
	 */
	public void getRating() {
		final String uri = Helpers.buildResourceUri("/beers/28/ratings/saeufer");
		final JSONObject ratingJson = getJSONObject(uri);
		
		try {
			Assert.assertEquals("rating", ratingJson.get("type"));
			Assert.assertEquals(3, ratingJson.getInt("rating"));
		} catch (JSONException e) {
			Assert.fail();
			e.printStackTrace();
		}
	}
	
}
