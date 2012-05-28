package ch.hsr.bieridee.test.resources;

import junit.framework.Assert;

import org.apache.http.HttpResponse;
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

	/**
	 * Tests the timeline filter by user function.
	 */
	@Test
	public void retrieveFilteredTimeline() {
		final String username = "jonas";
		final String request = Res.PUBLIC_API_URL + Res.TIMELINE_COLLECTION + "?username=" + username;
		final JSONArray timelineJson = getJSONArray(request);

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

	/**
	 * Tests the timeline item limitation.
	 */
	@Test
	public void retrieveLimitedTimeline() {
		final int pageSize = 4;
		final String request = Res.PUBLIC_API_URL + Res.TIMELINE_COLLECTION + "?items=" + pageSize;
		final JSONArray timelineJson = getJSONArray(request);

		Assert.assertEquals(pageSize, timelineJson.length());
	}

	/**
	 * Tests the timeline item limitation.
	 */
	@Test
	public void retrieveLimitedFilteredTimeline() {
		final String username = "jonas";
		final int pageSize = 4;
		final String request = Res.PUBLIC_API_URL + Res.TIMELINE_COLLECTION + "?username=" + username + "&items=" + pageSize;
		final JSONArray timelineJson = getJSONArray(request);

		Assert.assertEquals(pageSize, timelineJson.length());

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

	/**
	 * Tests the paged timeline.
	 */
	@Test
	public void retrievePagedTimeline() {
		final int pageSize = 4;
		final String request0 = Res.PUBLIC_API_URL + Res.TIMELINE_COLLECTION + "?items=" + pageSize + "&page=" + 0;
		final JSONArray timelineJsonPage0 = getJSONArray(request0);
		final String request1 = Res.PUBLIC_API_URL + Res.TIMELINE_COLLECTION + "?items=" + pageSize + "&page=" + 1;
		final JSONArray timelineJsonPage1 = getJSONArray(request1);

		Assert.assertEquals(pageSize, timelineJsonPage0.length());
		Assert.assertEquals(pageSize, timelineJsonPage1.length());
		Assert.assertFalse(timelineJsonPage0.equals(timelineJsonPage1));
	}

	/**
	 * Tests the paged timeline.
	 */
	@Test
	public void retrievePagedFilteredTimeline() {
		final String username = "jonas";
		final int pageSize = 2;
		final String request0 = Res.PUBLIC_API_URL + Res.TIMELINE_COLLECTION + "?username=" + username + "&items=" + pageSize + "&page=" + 0;
		final JSONArray timelineJsonPage0 = getJSONArray(request0);
		final String request1 = Res.PUBLIC_API_URL + Res.TIMELINE_COLLECTION + "?username=" + username + "&items=" + pageSize + "&page=" + 1;
		final JSONArray timelineJsonPage1 = getJSONArray(request1);

		Assert.assertEquals(pageSize, timelineJsonPage0.length());
		Assert.assertEquals(pageSize, timelineJsonPage1.length());
		Assert.assertFalse(timelineJsonPage0.equals(timelineJsonPage1));

		for (int i = 0; i < timelineJsonPage0.length(); ++i) {
			try {
				final JSONObject jsonObject = (JSONObject) timelineJsonPage0.get(i);
				final JSONObject user = jsonObject.getJSONObject("user");
				Assert.assertEquals(username, user.get("user"));
			} catch (JSONException e) {
				Assert.fail();
				e.printStackTrace();
			}
		}

		for (int i = 0; i < timelineJsonPage1.length(); ++i) {
			try {
				final JSONObject jsonObject = (JSONObject) timelineJsonPage1.get(i);
				final JSONObject user = jsonObject.getJSONObject("user");
				Assert.assertEquals(username, user.get("user"));
			} catch (JSONException e) {
				Assert.fail();
				e.printStackTrace();
			}
		}
	}

	/**
	 * Tests whether a HTTP 404 error occcurs, if an non existing user is provided as filter argument.
	 */
	@Test
	public void retrieveTimelineWithNonExistingUser() {
		final String username = "jonas12";
		final String uri = Res.PUBLIC_API_URL + Res.TIMELINE_COLLECTION + "?username=" + username;
		final HttpResponse response = getResponse(uri);
		Assert.assertEquals(404, response.getStatusLine().getStatusCode());
	}
}
