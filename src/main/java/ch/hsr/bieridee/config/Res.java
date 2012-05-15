package ch.hsr.bieridee.config;

import ch.hsr.bieridee.models.AbstractModel;
import ch.hsr.bieridee.models.BeerModel;
import ch.hsr.bieridee.models.BeertypeModel;
import ch.hsr.bieridee.models.BreweryModel;
import ch.hsr.bieridee.models.TagModel;
import ch.hsr.bieridee.models.UserModel;

/**
 * Resources.
 */
public final class Res {

	private Res() {
		// do not instantiate.
	}

	public static final String PUBLIC_API_URL = "http://" + Config.API_HOSTSTRING;

	// beer
	public static final String BEER_COLLECTION = "/beers";
	public static final String BEER_REQ_ATTR = "beer-id";
	public static final String BEER_DOCUMENT = "/beers/{" + BEER_REQ_ATTR + "}";
	public static final String BEER_FILTER_PARAMETER_TAG = "tag";

	// beertype
	public static final String BEERTYPE_COLLECTION = "/beertypes";
	public static final String BEERTYPE_REQ_ATTR = "beertype-id";
	public static final String BEERTYPE_DOCUMENT = "/beertypes/{" + BEERTYPE_REQ_ATTR + "}";

	// brewery
	public static final String BREWERY_COLLECTION = "/breweries";
	public static final String BREWERY_REQ_ATTR = "brewery-id";
	public static final String BREWERY_DOCUMENT = "/breweries/{" + BREWERY_REQ_ATTR + "}";
	public static final String BREWERY_FILTER_PARAMETER_SIZE = "size";

	// tag
	public static final String TAG_COLLECTION = "/tags";
	public static final String TAG_REQ_ATTR = "tag-id";
	public static final String TAG_DOCUMENT = "/tags/{" + TAG_REQ_ATTR + "}";

	// image
	public static final String IMAGE_COLLECTION = "/images";
	public static final String IMAGE_REQ_ATTR = "image-id";
	public static final String IMAGE_DOCUMENT = "/images/{" + IMAGE_REQ_ATTR + "}";

	// user
	public static final String USER_COLLECTION = "/users";
	public static final String USER_REQ_ATTR = "user-name";
	public static final String USER_DOCUMENT = "/users/{" + USER_REQ_ATTR + "}";
	public static final String USER_RECOMMENDATION_COLLECTION = USER_DOCUMENT + "/recommendations";

	// rating
	public static final String RATING_COLLECTION = "/ratings";
	public static final String RATING_DOCUMENT = BEER_DOCUMENT + RATING_COLLECTION + "/{" + USER_REQ_ATTR + "}";

	// consumption
	public static final String CONSUMPTION_COLLECTION = "/consumptions";
	public static final String CONSUMPTION_DOCUMENT = BEER_DOCUMENT + CONSUMPTION_COLLECTION + "/{" + USER_REQ_ATTR + "}";
	public static final String CONSUMPTION_BEER_COLLECTION = BEER_DOCUMENT + CONSUMPTION_COLLECTION;

	// timeline
	public static final String TIMELINE_COLLECTION = "/timeline";
	public static final String TIMELINE_FILTER_PARAMETER_USER = "username";
	public static final String TIMELINE_PAGE_PARAMETER = "page";
	public static final String TIMELINE_LIST_SIZE_PARAMETER = "nOfItems";
	
	// load test resource
	public static final String LOADTEST = "/mu-de20a9df-45567641-e6aff7ea-3179451e.txt";

	/**
	 * Returns the resource URI of the given domain object.
	 * 
	 * @param model
	 *            Model
	 * @return The resource URI, or null if no matching URI could be found
	 */
	public static String getResourceUri(AbstractModel model) {
		final Class<? extends AbstractModel> c = model.getClass();
		String uri = null;
		final String pattern = "\\{.*\\}";

		if (c == BeerModel.class) {
			final long id = ((BeerModel) model).getId();
			uri = PUBLIC_API_URL + BEER_DOCUMENT.replaceAll(pattern, Long.toString(id));
		}
		if (c == BeertypeModel.class) {
			final long id = ((BeertypeModel) model).getId();
			uri = PUBLIC_API_URL + BEERTYPE_DOCUMENT.replaceAll(pattern, Long.toString(id));
		}
		if (c == BreweryModel.class) {
			final long id = ((BreweryModel) model).getId();
			uri = PUBLIC_API_URL + BREWERY_DOCUMENT.replaceAll(pattern, Long.toString(id));
		}
		if (c == TagModel.class) {
			final long id = ((TagModel) model).getId();
			uri = PUBLIC_API_URL + TAG_DOCUMENT.replaceAll(pattern, Long.toString(id));
		}
		if (c == UserModel.class) {
			final String name = ((UserModel) model).getUsername();
			uri = Res.PUBLIC_API_URL + USER_DOCUMENT.replaceAll(pattern, name);
		}

		return uri;
	}

}
