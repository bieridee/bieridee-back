package ch.hsr.bieridee.utils;

import ch.hsr.bieridee.config.NodeProperty;
import ch.hsr.bieridee.config.NodeType;

/**
 * Utility Class providing predefined Cypher Query Strings.
 */
public final class Cypherqueries {
	private Cypherqueries() {
		// do not instantiate.
	}

	// Beers
	public static final String GET_ALL_BEERS = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_BEER]-BEER_INDEX-[:INDEXES]->Beer RETURN Beer ORDER BY Beer.name ASC";
	public static final String GET_BEER_BY_NAME = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_BEER]-BEER_INDEX-[:INDEXES]->Beer WHERE Beer.name = \'$$\' RETURN Beer";
	public static final String GET_BEERS_BY_TAG_ID = "START TAG_NODE = node($$) MATCH TAG_NODE<-[:HAS_TAG]-Beer RETURN Beer ORDER BY Beer.name ASC";
	public static final String GET_BEER_INDEX_NODE = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_BEER]->BEER_INDEX RETURN BEER_INDEX";

	// Users
	public static final String GET_ALL_USERS = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_USER]-USER_INDEX-[:INDEXES]->User RETURN User ORDER BY User.username ASC";
	public static final String GET_USER_BY_NAME = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_USER]-USER_INDEX-[:INDEXES]->User WHERE User.username = \'$$\' RETURN User";
	public static final String GET_USER_INDEX_NODE = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_USER]->USER_INDEX RETURN USER_INDEX";

	// Tags
	public static final String GET_ALL_TAGS = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_TAG]-TAG_INDEX-[:INDEXES]->Tag RETURN Tag ORDER BY Tag.name ASC";
	public static final String GET_TAG_BY_NAME = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_TAG]-TAG_INDEX-[:INDEXES]->Tag WHERE Tag.name = \'$$\' RETURN Tag";
	public static final String GET_TAG_INDEX = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_TAG]-TAG_INDEX RETURN TAG_INDEX";

	// Beertypes
	public static final String GET_ALL_BEERTYPES = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_BEERTYPE]-BEERTYPE_INDEX-[:INDEXES]->Beertype RETURN Beertype ORDER BY Beertype.name ASC";

	// Breweries
	public static final String GET_BREWERY_INDEX_NODE = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_BREWERY]->BREWERY_INDEX RETURN BREWERY_INDEX";
	public static final String GET_ALL_BREWERIES = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_BREWERY]-BREWERY_INDEX-[:INDEXES]->Brewery RETURN Brewery ORDER BY Brewery.name ASC";
	public static final String GET_BREWERIES_BY_TAG_NAME = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_BREWERY]-BREWERY_INDEX-[:INDEXES]->Brewery WHERE Brewery.size = \'$$\' RETURN Brewery ORDER BY Brewery.name ASC";

	// Consumptions
	public static final String GET_ALL_USER_CONSUMPTIONS = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_USER]-USER_INDEX-[:INDEXES]-User-[:HAS_CONSUMED]-Beer RETURN Beer";
	public static final String GET_ALL_CONSUMPTIONS = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_TIMELINESTART]-TIMELINESTART-[:NEXT*]-Action-[:NEXT]->TIMELINEEND WHERE Action.type = '" + NodeType.CONSUMPTION
			+ "' RETURN Action ORDER BY Action.timestamp DESC";
	public static final String GET_ALL_CONSUMPTIONS_FOR_BEER = "START beer = node($$) MATCH beer<-[:CONTAINS]-consumption WHERE consumption.type = '" + NodeType.CONSUMPTION + "' RETURN consumption ORDER BY consumption.timestamp DESC";
	public static final String GET_ALL_BEER_CONSUMPTIONS_FOR_USER = "START beer = node($$) MATCH beer<-[:CONTAINS]-consumption<-[:DOES]-user WHERE user.username = \'$$\' AND consumption.type = '" + NodeType.CONSUMPTION
			+ "' RETURN consumption ORDER BY consumption.timestamp DESC";

	// Timeline
	public static final String GET_TIMELINE = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_TIMELINESTART]-TIMELINESTART-[:NEXT*]-Action-[:NEXT]->TIMELINEEND RETURN Action";
	public static final String GET_TIMELINE_INDEX_NODE = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_TIMELINE]->TIMELINE_INDEX RETURN TIMELINE_INDEX";
	public static final String GET_TIMELINE_FOR_USER = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_TIMELINESTART]-TIMELINESTART-[:NEXT*]-Action-[:NEXT]->TIMELINEEND, Action<-[:DOES]-User WHERE User.username=\'$$\' RETURN Action";

	// Rating
	public static final String GET_AVERAGE_RATING_OF_BEER = "START START_BEER = node($$) MATCH START_BEER<-[:CONTAINS]-Action-[:INDEXES_ACTIVE]-() WHERE Action.type = '" + NodeType.RATING + "' RETURN AVG(Action." + NodeProperty.Rating.RATING
			+ ") AS AverageRating";
	public static final String GET_RATING = "START beer = NODE($$) MATCH beer<-[:CONTAINS]-Action-[:DOES]-User where Action.type='rating' and User.username='$$' RETURN Action AS Rating, User ORDER BY Action.timestamp DESC LIMIT 1";
	public static final String GET_ALL_RATINGS = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_TIMELINESTART]-TIMELINESTART-[:NEXT*]-Action-[:NEXT]->TIMELINEEND WHERE Action.type = '" + NodeType.RATING + "' RETURN Action";
	public static final String GET_ACTIVE_RATING = "START HOME_NODE = node(0), beer = node($$) MATCH HOME_NODE-[:INDEX_ACTIVERATINGINDEX]-ACTIVERATINGINDEX-[:INDEXES_ACTIVE]->Rating<-[:DOES]-User, Rating-[:CONTAINS]-beer WHERE User.username='$$' and Rating.type='rating' RETURN Rating, beer";

	// Recommendations
	public static final String GET_USER_RATED_BEERS = "START USER = node($$) MATCH USER-[:DOES]-Action-[:CONTAINS]->Beer, Action-[:INDEXES_ACTIVE]-() WHERE Action.type='rating' AND Action.rating >= 3 RETURN Beer ORDER BY Action.rating DESC";
	public static final String GET_FRIEND_RATINGS = "START USER = node($$) MATCH USER-[:DOES]-Action-[:CONTAINS]-Beer<-[:CONTAINS]-Friendaction-[:DOES]-Friend WHERE Action.type = 'rating' AND Friendaction.type='rating' AND Action.rating >= 3 AND Friendaction.rating >= 3 AND NOT(ID(Friend) = ID(USER)) RETURN Friendaction";
	// public static final String GET_AVG_RATING_FOR_BEERTYPES =
	// "START USER = node($$) MATCH USER-[:DOES]-Action-[:CONTAINS]-Beer-[:HAS_BEERTYPE]-beertype, Action-[:INDEXES_ACTIVE]-() WHERE Action.type='rating' AND Action.rating >= 3 RETURN DISTINCT ID(beertype)";
	public static final String GET_ACTIVE_RATINGS_FOR_USER_GREATER_3 = "START USER = node($$) MATCH USER-[:DOES]-Action-[:CONTAINS]-Beer-[:HAS_BEERTYPE]-beertype, Action-[:INDEXES_ACTIVE]-() WHERE Action.type='rating' AND Action.rating >= 3 RETURN Action";
	// public static final String GET_MY_CONSUMED_BEERS =
	// "START USER = node($$) MATCH USER-[:DOES]-Action-[:CONTAINS]-Beer WHERE Action.type='consumption' RETURN Beer";
	// public static final String GET_FRIEND_RATED_BEERS =
	// "START BEER = node ($$) MATCH User<-[:DOES]-Action-[:CONTAINS]-BEER WHERE Action.type='rating' AND not(Beer != BEER RETURN USER ORDER BY Action.rating DESC";
	// Brand
	public static final String GET_ALL_BRANDS = "START HOME = node(0) MATCH HOME-[:INDEX_BEER]->()-[:INDEXES]->Beer RETURN DISTINCT Beer.brand AS Brand ORDER BY Beer.brand ASC";

	// Beertype
	public static final String GET_BEERTYPE_INDEX_NODE = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_BEERTYPE]->BEERTYPE_INDEX RETURN BEERTYPE_INDEX";

	// Unknown
	public static final String GET_UNKNOWN_NODE = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_UNKNOWN]->()-[:INDEXES]->unknown WHERE unknown.type = \'$$\' RETURN unknown";
}
