package ch.hsr.bieridee.test.resources;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;

import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.test.ServerTest;
import ch.hsr.bieridee.test.helpers.Helpers;
import ch.hsr.bieridee.utils.NodeProperty;

/**
 * Test the creation of a new user via PUT on the user resource.
 * 
 */
public class BeerResourceTest extends ServerTest {

	/**
	 * Tests the creation of a new user.
	 */
	@Test
	public void receiveSingleBeer() {
		final String uri = Helpers.buildResourceUri(Res.BEER_COLLECTION + "/30");
		System.out.println(uri);
		final ClientResource clientResource = new ClientResource(uri);

		final Representation beerRepresentation = clientResource.get(MediaType.APPLICATION_JSON);

		JSONObject beerJSON = null;
		try {
			beerJSON = new JSONObject(beerRepresentation.getText());
		} catch (IOException e) {
			Assert.fail();
			e.printStackTrace();
		} catch (JSONException e) {
			Assert.fail();
			e.printStackTrace();
		}

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

		clientResource.release();
	}
}
