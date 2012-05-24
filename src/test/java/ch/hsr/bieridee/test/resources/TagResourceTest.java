package ch.hsr.bieridee.test.resources;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.neo4j.graphdb.NotFoundException;

import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BeerModel;
import ch.hsr.bieridee.models.TagModel;
import ch.hsr.bieridee.utils.DBUtil;

/**
 * Tests to test the beerlist resource. (Bonjour Captain Obviours)
 * 
 */
public class TagResourceTest extends ResourceTest {

	/**
	 * Tests the creation of a beer via post request.
	 */
	@Test
	public void createTag() {

		final String name = "delicious";
		final long tagId = 28;

		final JSONObject newTagJSON = new JSONObject();
		try {
			newTagJSON.put("value", name);
		} catch (JSONException e) {
			Assert.fail();
			e.printStackTrace();
		}
		final String uri = Res.PUBLIC_API_URL + Res.TAG_COLLECTION + "?" + Res.TAG_FILTER_PARAMETER_BEER + "=" + tagId;
		final String newTagJSONString = postJson(uri, newTagJSON);

		try {
			final JSONObject tag = new JSONObject(newTagJSONString);
			Assert.assertEquals(name, tag.getString("name"));

		} catch (JSONException e) {
			Assert.fail();
			e.printStackTrace();
		} catch (NotFoundException e) {
			Assert.fail();
			e.printStackTrace();
		}

		BeerModel bm = null;
		try {
			final TagModel shouldContain = new TagModel(DBUtil.getTagByName(name));
			bm = new BeerModel(tagId);
			if (!bm.getTagModels().contains(shouldContain)) {
				Assert.fail("Tag was not added to Beer!");
			}
		} catch (NotFoundException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (WrongNodeTypeException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	/**
	 * Create a tag with an already existing name.
	 */
	@Test
	public void createAlreadyExistingTag() {
		final long beerId = 28;
		final long tagId = 47;
		final String name = "hell";

		final JSONObject newBeerJson = new JSONObject();
		try {
			newBeerJson.put("value", name);
		} catch (JSONException e) {
			Assert.fail();
			e.printStackTrace();
		}
		final String uri = Res.PUBLIC_API_URL + Res.TAG_COLLECTION + "?" + Res.TAG_FILTER_PARAMETER_BEER + "=" + beerId;
		final String newTagJSONString = postJson(uri, newBeerJson);
		JSONObject tagObject = null;
		try {
			tagObject = new JSONObject(newTagJSONString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Assert.assertEquals(tagId, tagObject.optLong("id"));

	}

	/**
	 * 
	 */
	@Test
	public void obtainAllTagsForBeer() {
		
		final long beerId = 28;

		final JSONArray tagArray = getJSONArray(Res.PUBLIC_API_URL + Res.BEER_COLLECTION + "/28" + Res.TAG_COLLECTION);
		BeerModel bm;
		List<TagModel> tagModelsfromJSON = null;
		List<TagModel> tagModel = null;
		try {
			bm = new BeerModel(beerId);
			tagModel = bm.getTagModels();
			tagModelsfromJSON = new LinkedList<TagModel>();
			for (int i = 0; i < tagArray.length(); ++i) {
				final JSONObject tagObj = tagArray.getJSONObject(i);
				final long id = tagObj.optLong("id");
				tagModelsfromJSON.add(new TagModel(id));
			}
		} catch (NotFoundException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (WrongNodeTypeException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (JSONException e) {
			e.printStackTrace();
			Assert.fail();
		}
		Assert.assertTrue(tagModel.containsAll(tagModelsfromJSON));
	}

}
