package ch.hsr.bieridee.utils;

/**
 * Utility Class providing predefined Cypher Query Strings.
 */
public final class Cypherqueries {
	private Cypherqueries() {
		// do not instantiate.
	}

	// Beers
	public static final String GET_ALL_BEERS = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_BEER]-BEER_INDEX-[:INDEXES]->Beer RETURN Beer";
	public static final String GET_BEER_BY_NAME = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_BEER]-BEER_INDEX-[:INDEXES]->Beer WHERE Beer.name = \'$1\' RETURN Beer";
	public static final String GET_BEERS_BY_TAG_NAME = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_TAG]-()-[:INDEXES]-TAG<-[:HAS_TAG]-Beer WHERE TAG.name = \'$1\'  RETURN Beer";
	
	// Users
	public static final String GET_ALL_USERS = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_USER]-USER_INDEX-[:INDEXES]->User RETURN User";
	public static final String GET_USER_BY_NAME = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_USER]-USER_INDEX-[:INDEXES]->User WHERE User.username = \'$1\' RETURN User";
	public static final String GET_USER_INDEX_NODE = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_USER]->USER_INDEX RETURN USER_INDEX";
	
	// Tags
	public static final String GET_ALL_TAGS = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_TAG]-TAG_INDEX-[:INDEXES]->Tag RETURN Tag";
	public static final String GET_TAG_BY_NAME = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_TAG]-TAG_INDEX-[:INDEXES]->Tag WHERE Tag.name = \'$1\' RETURN Tag";
	
	// Beertypes
	public static final String GET_ALL_BEERTYPES = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_BEERTYPE]-BEERTYPE_INDEX-[:INDEXES]->Beertype RETURN Beertype";
	
	// Breweries
	public static final String GET_ALL_BREWERIES = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_BREWERY]-BREWERY_INDEX-[:INDEXES]->Brewery RETURN Brewery";
	public static final String GET_BREWERIES_BY_TAG_NAME = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_BREWERY]-BREWERY_INDEX-[:INDEXES]->Brewery WHERE Brewery.size = \'$1\' RETURN Brewery";
	
	// Consumptions
	public static final String GET_ALL_USER_CONSUMPTIONS = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_USER]-USER_INDEX-[:INDEXES]-User-[:HAS_CONSUMED]-Beer RETURN Beer";

    // Timeline
	public static final String GET_TIMELINE = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_TIMELINESTART]-TIMELINESTART-[:NEXT*]-Action-[:NEXT]->TIMELINEEND RETURN Action";
	public static final String GET_TIMELINE_INDEX_NODE = "START HOME_NODE = node(0) MATCH HOME_NODE-[:INDEX_TIMELINE]->TIMELINE_INDEX RETURN TIMELINE_INDEX";

}
