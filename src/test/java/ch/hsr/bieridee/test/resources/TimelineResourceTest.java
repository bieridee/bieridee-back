package ch.hsr.bieridee.test.resources;

import junit.framework.Assert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import ch.hsr.bieridee.config.Res;

/**
 * Tests the timeline resource.
 * 
 */
public class TimelineResourceTest extends ResourceTest {

	/**
	 * Tests the retrieval of the timeline.
	 */
	@Test
	public void retrieveTimeline() {

		final JSONArray timelineJson = getJSONArray(Res.PUBLIC_API_URL + Res.TIMELINE_COLLECTION);
		JSONObject firstEntry = null;
		JSONObject thirdEntry = null;
		try {
			firstEntry = (JSONObject) timelineJson.get(0);
			thirdEntry = (JSONObject) timelineJson.get(2);
		} catch (JSONException e) {
			Assert.fail();
			e.printStackTrace();
		}

		Assert.assertNotNull(firstEntry);
		Assert.assertNotNull(thirdEntry);
		try {
			Assert.assertEquals("rating", firstEntry.get("type"));
			Assert.assertEquals(3, firstEntry.get("rating"));
			final long firstTimestamp = (Long) firstEntry.get("timestamp");
			Assert.assertEquals("consumption", thirdEntry.get("type"));
			final long thirdTimestamp = (Long) thirdEntry.get("timestamp");
			Assert.assertTrue(firstTimestamp > thirdTimestamp);
		} catch (JSONException e) {
			Assert.fail();
			e.printStackTrace();
		}

	}

	@Test
	public void retrieveFilteredTimeline() {
		final String username = "alki";
		final String request = Res.PUBLIC_API_URL + Res.TIMELINE_COLLECTION + "?username=" + username;
		final JSONArray timelineJson = getJSONArray(request);
		System.out.println("request: " + request + "\tjson: " + timelineJson);

		for (int i = 0; i < timelineJson.length(); ++i) {
			try {
				final JSONObject jsonObject = (JSONObject) timelineJson.get(i);
				final JSONObject user = jsonObject.getJSONObject("user");
				Assert.assertEquals(username, user.get("user"));
			} catch (JSONException e) {
				Assert.fail();
				e.printStackTrace();
			}
		}
	}
}
