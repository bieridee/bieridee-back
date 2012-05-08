package ch.hsr.bieridee.test.resources;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import ch.hsr.bieridee.config.NodeProperty;
import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.test.helpers.Helpers;

/**
 * Test the creation of a new user via PUT on the user resource.
 * 
 */
public class BeerResourceTest extends ResourceTest {

	/**
	 * Tests the creation of a new user.
	 */
	@Test
	public void receiveSingleBeer() {
		final String uri = Helpers.buildResourceUri(Res.BEER_COLLECTION + "/30");
		final JSONObject beerJSON = getJSONObject(uri);

		Assert.assertNotNull(beerJSON);
		try {
			Assert.assertEquals(beerJSON.get(NodeProperty.Beer.NAME), "Calanda Meisterbräu");
			Assert.assertEquals(Res.PUBLIC_API_URL + Res.IMAGE_COLLECTION + "/", beerJSON.get(NodeProperty.Beer.IMAGE));
			Assert.assertEquals(beerJSON.get(NodeProperty.Beer.BRAND), "Calanda");

			final JSONObject beertype = beerJSON.getJSONObject("beertype");
			Assert.assertEquals(beertype.get(NodeProperty.Beertype.NAME), "Pils");
			Assert.assertEquals(beertype.get("uri"), Res.PUBLIC_API_URL + "/beertypes/26");

			final JSONObject brewery = beerJSON.getJSONObject("brewery");
			Assert.assertEquals(brewery.get(NodeProperty.Brewery.NAME), "Calanda");
			Assert.assertEquals(brewery.get("uri"), Res.PUBLIC_API_URL + "/breweries/65");

			final JSONArray tags = beerJSON.getJSONArray("tags");
			final JSONObject t2 = tags.getJSONObject(0);
			final JSONObject t1 = tags.getJSONObject(1);

			Assert.assertEquals("suess", t1.get(NodeProperty.Tag.NAME));
			Assert.assertEquals(Res.PUBLIC_API_URL + "/tags/suess", t1.get("uri"));

			Assert.assertEquals(t2.get(NodeProperty.Tag.NAME), "lecker");
			Assert.assertEquals(Res.PUBLIC_API_URL + "/tags/lecker", t2.get("uri"));

		} catch (JSONException e) {
			Assert.fail();
			e.printStackTrace();
		}

	}
}
