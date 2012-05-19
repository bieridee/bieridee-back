package ch.hsr.bieridee.test.resources;

import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BeertypeModel;
import ch.hsr.bieridee.models.BreweryModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.neo4j.graphdb.NotFoundException;


/**
 * Tests to test the beerlist resource. (Bonjour Captain Obviours)
 *
 */
public class BeerListResourceTest extends ResourceTest {
	
	/**
	 * Tests the creation of a beer via post request.
	 */
	@Test
	public void createBeer() {
		
		final String name = "Leermond Bier";
		final String brand = "Appenzeller Bier";
		final long beertypeId = 22;
		final long breweryId = 71;
		
		final JSONObject newBeerJson = new JSONObject();
		try {
			newBeerJson.put("name", name);
			newBeerJson.put("brand", brand);
			newBeerJson.put("beertype", beertypeId);
			newBeerJson.put("brewery", breweryId);
		} catch (JSONException e) {
			Assert.fail();
			e.printStackTrace();
		}
		final String uri = Res.PUBLIC_API_URL + Res.BEER_COLLECTION;
		final String newBeerJSONString = postJson(uri, newBeerJson);
		
		try {
			final JSONObject beer = new JSONObject(newBeerJSONString);
			Assert.assertEquals(name, beer.getString("name"));
			Assert.assertEquals(brand, beer.getString("brand"));
			
			Assert.assertEquals(Res.getResourceUri(new BreweryModel(breweryId)), beer.getJSONObject("brewery").getString("uri"));
			Assert.assertEquals(Res.getResourceUri(new BeertypeModel(beertypeId)), beer.getJSONObject("beertype").getString("uri"));
			
		} catch (JSONException e) {
			Assert.fail();
			e.printStackTrace();
		} catch (NotFoundException e) {
			Assert.fail();
			e.printStackTrace();
		} catch (WrongNodeTypeException e) {
			Assert.fail();
			e.printStackTrace();
		}
	}
	
	/**
	 * Tests the creation of a beer with unknown brewery and beertype.
	 */
	@Test
	public void createBeerWithUnknownBreweryAndBeertype() {
		final String name = "Nastro Azzurro";
		final String brand = "Azzurro";
		
		final JSONObject newBeerJson = new JSONObject();
		try {
			newBeerJson.put("name", name);
			newBeerJson.put("brand", brand);
			newBeerJson.put("unknownbrewery", true);
			newBeerJson.put("unknownbeertype", true);
		} catch (JSONException e) {
			Assert.fail();
			e.printStackTrace();
		}
		
		final String uri = Res.PUBLIC_API_URL + Res.BEER_COLLECTION;
		final String newBeerJSONString = postJson(uri, newBeerJson);
		
		try {
			final JSONObject beer = new JSONObject(newBeerJSONString);
			
			Assert.assertEquals(name, beer.getString("name"));
			Assert.assertEquals(brand, beer.getString("brand"));
			
			Assert.assertEquals(true, beer.getJSONObject("brewery").getBoolean("unknown"));
			Assert.assertEquals(true, beer.getJSONObject("beertype").getBoolean("unknown"));
			
		} catch (JSONException e) {
			Assert.fail();
			e.printStackTrace();
		} catch (NotFoundException e) {
			Assert.fail();
			e.printStackTrace();
		}
	}
	
}
