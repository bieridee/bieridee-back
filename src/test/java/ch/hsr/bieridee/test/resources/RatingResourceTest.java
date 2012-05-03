package ch.hsr.bieridee.test.resources;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;

import ch.hsr.bieridee.config.NodeType;
import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BeerModel;
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

		putJSON(Res.API_URL + "/beers/30/ratings/alki", rating);
		final List<Node> ratingNodes = DBUtil.getTimeLine(1);
		
		final Node ratingNode = ratingNodes.get(0);
		Assert.assertEquals(NodeType.RATING, ratingNode.getProperty(NodeProperty.TYPE));
		Assert.assertEquals(4, ratingNode.getProperty(NodeProperty.Rating.RATING));
	}
	
	

	
}
