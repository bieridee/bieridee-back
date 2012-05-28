package ch.hsr.bieridee.test.resources;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.neo4j.graphdb.NotFoundException;

import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BreweryModel;

/**
 * Tests for the beer consumptions.
 * 
 */
public class BreweryListResourceTest extends ResourceTest {

	/**
	 * Tests the creation of a new beertype.
	 */
	@Test
	public void createBrewery() {
		final String uri = Res.PUBLIC_API_URL + Res.BREWERY_COLLECTION;
		final JSONObject breweryJSON = new JSONObject();
		final String name = "Z-Augenbräu";
		final String description = "Das Beste Brauerei im Zürcher Oberland! Gelegen im schönen idyllischen Rüti.";
		final String size = "XS";
		final String picture = "brewery123.jpg";

		try {
			breweryJSON.put("name", name);
			breweryJSON.put("description", description);
			breweryJSON.put("size", size);
			breweryJSON.put("picture", picture);
		} catch (JSONException e) {
			e.printStackTrace();
			Assert.fail();
		}

		final String breweryJSONString = this.postJson(uri, breweryJSON);
		try {
			final JSONObject responseObject = new JSONObject(breweryJSONString);
			final BreweryModel bm = new BreweryModel(responseObject.getLong("id"));

			Assert.assertEquals(responseObject.getString("name"), name);
			Assert.assertEquals(responseObject.getString("description"), description);
			Assert.assertEquals(responseObject.getString("size"), size);
			Assert.assertEquals(responseObject.getString("picture"), picture);
			Assert.assertEquals(Res.getResourceUri(bm), responseObject.getString("uri"));
		} catch (JSONException e1) {
			e1.printStackTrace();
			Assert.fail();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (NotFoundException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (WrongNodeTypeException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Requests all beertypes and checks if the first matches ale beertype.
	 */
	@Test
	public void getAllBreweries() {
		final String uri = Res.PUBLIC_API_URL + Res.BREWERY_COLLECTION;
		final JSONArray array = this.getJSONArray(uri);
		try {
			final BreweryModel bm = new BreweryModel(71);
			final JSONObject locher = array.getJSONObject(0);
			Assert.assertEquals(bm.getName(), locher.getString("name"));
			Assert.assertEquals(bm.getDescription(), locher.getString("description"));
			Assert.assertEquals(bm.getId(), locher.getLong("id"));
		} catch (JSONException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (NotFoundException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (WrongNodeTypeException e) {
			e.printStackTrace();
			Assert.fail();
		}

	}

	/**
	 * Tests the size filter.
	 */
	@Test
	public void retrieveFilteredBeertypeList() {
		final String uri = Res.PUBLIC_API_URL + Res.BREWERY_COLLECTION + "?size=national";
		final JSONArray jsonBreweries = this.getJSONArray(uri);

		for (int i = 0; i < jsonBreweries.length(); ++i) {
			final JSONObject jsonBrewery = jsonBreweries.optJSONObject(i);
			Assert.assertEquals("national", jsonBrewery.optString("size"));
		}
	}

	/**
	 * Tests the limited beertypelist.
	 */
	@Test
	public void retrieveLimitedBeertypeList() {
		final int pageSize = 5;
		final String uri = Res.PUBLIC_API_URL + Res.BREWERY_COLLECTION + "?items=" + pageSize;
		final JSONArray jsonBreweries = this.getJSONArray(uri);

		Assert.assertEquals(pageSize, jsonBreweries.length());

	}

	/**
	 * Tests the limited beertypelist.
	 */
	@Test
	public void retrieveLimitedFilterdBeertypeList() {
		final int pageSize = 3;
		final String uri = Res.PUBLIC_API_URL + Res.BREWERY_COLLECTION + "?size=national&items=" + pageSize;
		final JSONArray jsonBreweries = this.getJSONArray(uri);

		Assert.assertEquals(pageSize, jsonBreweries.length());

		for (int i = 0; i < jsonBreweries.length(); ++i) {
			final JSONObject jsonBrewery = jsonBreweries.optJSONObject(i);
			Assert.assertEquals("national", jsonBrewery.optString("size"));
		}

	}

	/**
	 * Tests the paged beertypelist.
	 */
	@Test
	public void retrievePagedBeertypeList() {
		final int pageSize = 2;
		final String uriPage0 = Res.PUBLIC_API_URL + Res.BREWERY_COLLECTION + "?items=" + pageSize + "&page=" + 0;
		final String uriPage1 = Res.PUBLIC_API_URL + Res.BREWERY_COLLECTION + "?items=" + pageSize + "&page=" + 1;
		final JSONArray jsonBreweries0 = this.getJSONArray(uriPage0);
		final JSONArray jsonBreweries1 = this.getJSONArray(uriPage1);

		Assert.assertEquals(pageSize, jsonBreweries0.length());
		Assert.assertEquals(pageSize, jsonBreweries1.length());
		Assert.assertFalse(jsonBreweries0.equals(jsonBreweries1));

	}
	
	/**
	 * Tests the paged filtered beertypelist.
	 */
	@Test
	public void retrievePagedFilterdBeertypeList() {
		final int pageSize = 2;
		final String uriPage0 = Res.PUBLIC_API_URL + Res.BREWERY_COLLECTION + "?size=national&items=" + pageSize + "&page=" + 0;
		final String uriPage1 = Res.PUBLIC_API_URL + Res.BREWERY_COLLECTION + "?size=national&items=" + pageSize + "&page=" + 1;
		final JSONArray jsonBreweries0 = this.getJSONArray(uriPage0);
		final JSONArray jsonBreweries1 = this.getJSONArray(uriPage1);
		
		Assert.assertEquals(pageSize, jsonBreweries0.length());
		Assert.assertEquals(pageSize, jsonBreweries1.length());
		Assert.assertFalse(jsonBreweries0.equals(jsonBreweries1));
		
		for (int i = 0; i < jsonBreweries0.length(); ++i) {
			final JSONObject jsonBrewery = jsonBreweries0.optJSONObject(i);
			Assert.assertEquals("national", jsonBrewery.optString("size"));
		}
		
		for (int i = 0; i < jsonBreweries1.length(); ++i) {
			final JSONObject jsonBrewery = jsonBreweries1.optJSONObject(i);
			Assert.assertEquals("national", jsonBrewery.optString("size"));
		}
		
	}

}
