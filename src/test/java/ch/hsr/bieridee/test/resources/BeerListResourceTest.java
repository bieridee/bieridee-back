package ch.hsr.bieridee.test.resources;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.neo4j.graphdb.NotFoundException;

import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BeertypeModel;
import ch.hsr.bieridee.models.BreweryModel;

/**
 * Tests to test the beerlist resource. (Bonjour Captain Obvious)
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

	/**
	 * Tests the limited beerlist.
	 */
	@Test
	public void retrieveLimitedBeerList() {
		final int pageSize = 4;
		final String request = Res.PUBLIC_API_URL + Res.BEER_COLLECTION + "?items=" + pageSize;
		final JSONArray beerlistJson = getJSONArray(request);

		Assert.assertEquals(pageSize, beerlistJson.length());
	}

	/**
	 * Tests the limited beerlist.
	 */
	@Test
	public void retrieveLimitedBeerListWithTagfilter() {
		final int tag = 38; // wuerzig
		final int pageSize = 1;
		final String request = Res.PUBLIC_API_URL + Res.BEER_COLLECTION + "?tag=" + tag + "&items=" + pageSize;
		final JSONArray beerlistJson = getJSONArray(request);

		Assert.assertEquals(pageSize, beerlistJson.length());

		for (int i = 0; i < beerlistJson.length(); ++i) {
			try {
				final JSONObject jsonObject = (JSONObject) beerlistJson.get(i);
				final JSONArray tags = jsonObject.getJSONArray("tags");
				final String tagsString = tags.toString();
				Assert.assertTrue(tagsString.contains("wuerzig"));
			} catch (JSONException e) {
				Assert.fail();
				e.printStackTrace();
			}
		}
	}

	/**
	 * Test the barcode filtering of the beer list resource.
	 */
	@Test
	public void barcodeFilter() {
		final String barcode = "7611889110662";
		final int expectedBeerId = 34;

		final String uri = Res.PUBLIC_API_URL + Res.BEER_COLLECTION + "?barcode=" + barcode;
		final JSONArray beers = getJSONArray(uri);

		// Assert that only and exactly one beer has been returned
		Assert.assertEquals(1, beers.length());

		// Assert that the correct beer has been returned
		try {
			final JSONObject beer = (JSONObject) beers.get(0);
			final int actualBeerId = beer.getInt("id");
			Assert.assertEquals(expectedBeerId, actualBeerId);
		} catch (JSONException e) {
			Assert.fail("JSON Exception");
		}
	}

	/**
	 * Tests the beerlist.
	 */
	@Test
	public void retrievePagedBeerlist() {
		final int pageSize = 1;
		final String request0 = Res.PUBLIC_API_URL + Res.BEER_COLLECTION + "?items=" + pageSize + "&page=" + 0;
		final JSONArray beerlistJsonPage0 = getJSONArray(request0);
		final String request1 = Res.PUBLIC_API_URL + Res.BEER_COLLECTION + "?items=" + pageSize + "&page=" + 1;
		final JSONArray beelistJsonPage1 = getJSONArray(request1);

		Assert.assertEquals(pageSize, beerlistJsonPage0.length());
		Assert.assertEquals(pageSize, beelistJsonPage1.length());
		Assert.assertFalse(beerlistJsonPage0.equals(beelistJsonPage1));
	}

	/**
	 * Tests the paged and filtered beerlist.
	 */
	@Test
	public void retrievePagedFilteredBeerlist() {
		final int tag = 38; // wuerzig
		final int pageSize = 1;
		final String request0 = Res.PUBLIC_API_URL + Res.BEER_COLLECTION + "?tag=" + tag + "&items=" + pageSize + "&page=" + 0;
		final JSONArray beerlistJsonPage0 = getJSONArray(request0);
		final String request1 = Res.PUBLIC_API_URL + Res.BEER_COLLECTION + "?tag=" + tag + "&items=" + pageSize + "&page=" + 1;
		final JSONArray beelistJsonPage1 = getJSONArray(request1);

		Assert.assertEquals(pageSize, beerlistJsonPage0.length());
		Assert.assertEquals(pageSize, beelistJsonPage1.length());
		Assert.assertFalse(beerlistJsonPage0.equals(beelistJsonPage1));

		for (int i = 0; i < beerlistJsonPage0.length(); ++i) {
			try {
				final JSONObject jsonObject = (JSONObject) beerlistJsonPage0.get(i);
				final JSONArray tags = jsonObject.getJSONArray("tags");
				final String tagsString = tags.toString();
				Assert.assertTrue(tagsString.contains("wuerzig"));
			} catch (JSONException e) {
				Assert.fail();
				e.printStackTrace();
			}
		}

		for (int i = 0; i < beelistJsonPage1.length(); ++i) {
			try {
				final JSONObject jsonObject = (JSONObject) beelistJsonPage1.get(i);
				final JSONArray tags = jsonObject.getJSONArray("tags");
				final String tagsString = tags.toString();
				Assert.assertTrue(tagsString.contains("wuerzig"));
			} catch (JSONException e) {
				Assert.fail();
				e.printStackTrace();
			}
		}
	}

}
