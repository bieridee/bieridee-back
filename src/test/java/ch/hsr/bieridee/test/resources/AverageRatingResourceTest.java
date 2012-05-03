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
public class AverageRatingResourceTest extends ResourceTest {
	
	@Test
	public void testNewAverateRatingCalculation() {
		final JSONObject rating = new JSONObject();
		try {
			rating.put(NodeProperty.Rating.RATING, 4);
		} catch (JSONException e) {
			Assert.fail();
			e.printStackTrace();
		}

		putJSON(Res.API_URL + "/beers/28/ratings/saeufer", rating);
		
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
				Assert.assertEquals(2.5, beerModel.getAverageRating());

	}
	
	@Test
	public void getAverageRating(){
		BeerModel beerModel = null;
		try {
			beerModel = new BeerModel(30);
		} catch (NotFoundException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (WrongNodeTypeException e) {
			e.printStackTrace();
			Assert.fail();
		}
		
		Assert.assertEquals(0.0, beerModel.getAverageRating());
		
	}

	
}
