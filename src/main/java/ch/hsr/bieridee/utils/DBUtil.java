package ch.hsr.bieridee.utils;

import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import ch.hsr.bieridee.config.NodeProperty;
import ch.hsr.bieridee.config.NodeType;
import ch.hsr.bieridee.config.RelType;
import ch.hsr.bieridee.models.BeerModel;
import ch.hsr.bieridee.models.UserModel;

/**
 * Utility to work with the neo4J graph-db.
 */
public final class DBUtil {

	private static EmbeddedGraphDatabase DB;

	public static void setDB(EmbeddedGraphDatabase db) {
		DB = db;
	}

	private DBUtil() {
		// do not instantiate
	}

	/**
	 * Gets the neo4j node by the absolute node id.
	 * 
	 * @param id
	 *            The id of the neo4j node
	 * @return The node with the given id
	 */
	public static Node getNodeById(long id) {
		final Transaction tx = DB.beginTx();
		Node node = null;
		try {
			node = DB.getNodeById(id);
			tx.success();
		} finally {
			tx.finish();
		}

		return node;
	}

	/**
	 * Gets a list of all beer nodes from the database.
	 * 
	 * @return List all existing beernodes.
	 */
	public static List<Node> getBeerNodeList() {
		return Cypher.executeAndGetNodes(Cypherqueries.GET_ALL_BEERS, "Beer");
	}

	/**
	 * Gets a list of all tags with the given tag.
	 * 
	 * @param tagId
	 *            Id of a Tag which is used as a filter.
	 * @return a List of Beers matching the given Tag name.
	 */
	public static List<Node> getBeerNodeList(long tagId) {
		return Cypher.executeAndGetNodes(Cypherqueries.GET_BEERS_BY_TAG_ID, "Beer", Long.toString(tagId));
	}

	/**
	 * Gets a list of all beertype nodes from the database.
	 * 
	 * @return List of all existing beertype nodes.
	 */
	public static List<Node> getBeertypeNodeList() {
		return Cypher.executeAndGetNodes(Cypherqueries.GET_ALL_BEERTYPES, "Beertype");
	}

	/**
	 * Gets a list of all brewery nodes from the database.
	 * 
	 * @return List of all existing brewery nodes.
	 */
	public static List<Node> getBreweryNodeList() {
		return Cypher.executeAndGetNodes(Cypherqueries.GET_ALL_BREWERIES, "Brewery");
	}

	/**
	 * Gets a list of all brewery nodes filtered by size.
	 * 
	 * @param brewerySize
	 *            Brewery size which is used as a filter.
	 * @return a List of Beers matching the given Tag name
	 */
	public static List<Node> getBreweryNodeList(String brewerySize) {
		return Cypher.executeAndGetNodes(Cypherqueries.GET_BREWERIES_BY_TAG_NAME, "Brewery", brewerySize);
	}

	/**
	 * Gets a list of all user nodes from the database.
	 * 
	 * @return List of all existing user nodes.
	 */
	public static List<Node> getUserNodeList() {
		return Cypher.executeAndGetNodes(Cypherqueries.GET_ALL_USERS, "User");
	}

	public static List<Node> getTagNodeList() {
		return Cypher.executeAndGetNodes(Cypherqueries.GET_ALL_TAGS, "Tag");
	}

	/**
	 * Gets a neo4j beer node by the name of the beer.
	 * 
	 * @param name
	 *            The name (name property) of the beer
	 * @return The beer with the given name or <code>null</code> if not found
	 */
	public static Node getBeerByName(String name) {
		return Cypher.executeAndGetSingleNode(Cypherqueries.GET_BEER_BY_NAME, "Beer", name);
	}

	/**
	 * Gets a neo4j beer node by the barcode.
	 *
	 * @param barcode
	 *            A barcode of the beer
	 * @return The beer associated with the given barcode or <code>null</code> if not found
	 */
	public static Node getBeerByBarcode(String barcode) {
		return Cypher.executeAndGetSingleNode(Cypherqueries.GET_BEER_BY_BARCODE, "Beer", barcode);
	}

	/**
	 * Gets a neo4j tag node by the name of the tag.
	 * 
	 * @param name
	 *            The name (name property) of the tag
	 * @return The tag with the given name or <code>null</code> if not found
	 */
	public static Node getTagByName(String name) {
		return Cypher.executeAndGetSingleNode(Cypherqueries.GET_TAG_BY_NAME, "Tag", name);
	}

	/**
	 * Gets a neo4j node by the username of the user.
	 * 
	 * @param name
	 *            Username
	 * @return The node
	 */
	public static Node getUserByName(String name) {
		return Cypher.executeAndGetSingleNode(Cypherqueries.GET_USER_BY_NAME, "User", name);
	}

	private static Node getUserIndex() {
		return Cypher.executeAndGetSingleNode(Cypherqueries.GET_USER_INDEX_NODE, "USER_INDEX");
	}

	private static Node getTimelineIndex() {
		return Cypher.executeAndGetSingleNode(Cypherqueries.GET_TIMELINE_INDEX_NODE, "TIMELINE_INDEX");
	}

	private static Node getBeerIndex() {
		return Cypher.executeAndGetSingleNode(Cypherqueries.GET_BEER_INDEX_NODE, "BEER_INDEX");
	}

	/**
	 * @param type
	 *            String with type
	 * @return index Node
	 */
	public static Node getIndex(String type) {
		Node indexNode = null;
		if (type.equals(NodeType.USER)) {
			indexNode = getUserIndex();
		}
		return indexNode;
	}

	/**
	 * Creates a bidirectional relationship between the given Nodes.
	 * 
	 * @param startNode
	 *            Start Node of the Relationship.
	 * @param relType
	 *            Type of the Relation to be created.
	 * @param endNode
	 *            End Node of the Relationship.
	 * @return The newly created Relationship
	 */
	public static Relationship createRelationship(Node startNode, RelType relType, Node endNode) {
		final Transaction transaction = DB.beginTx();
		Relationship rel = null;
		try {
			rel = startNode.createRelationshipTo(endNode, relType);
			transaction.success();
		} finally {
			transaction.finish();
		}
		return rel;
	}

	private static void connectUserNodeToIndex(Node blankNode) {
		final Node indexNode = getUserIndex();
		blankNode.setProperty(NodeProperty.TYPE, NodeType.USER);
		indexNode.createRelationshipTo(blankNode, RelType.INDEXES);
	}

	private static void connectActionNodeToIndex(Node blankNode, String actionType) {
		final Node indexNode = getTimelineIndex();
		blankNode.setProperty(NodeProperty.TYPE, actionType);
		indexNode.createRelationshipTo(blankNode, RelType.INDEXES);
	}

	private static void connectBeerNodeToIndex(Node blankNode) {
		final Node indexNode = getBeerIndex();
		blankNode.setProperty(NodeProperty.TYPE, NodeType.BEER);
		indexNode.createRelationshipTo(blankNode, RelType.INDEXES);
	}

	/**
	 * @param type
	 *            String with type
	 * @return created Node in the Database
	 */
	public static Node createNode(String type) {
		final Transaction transaction = DB.beginTx();
		Node newNode = null;
		try {
			newNode = DB.createNode();
			transaction.success();
			if (type.equals(NodeType.USER)) {
				connectUserNodeToIndex(newNode);
			}
			if (type.equals(NodeType.CONSUMPTION)) {
				connectActionNodeToIndex(newNode, NodeType.CONSUMPTION);
				addToTimeLine(newNode);
			}
			if (type.equals(NodeType.RATING)) {
				connectActionNodeToIndex(newNode, NodeType.RATING);
				addToTimeLine(newNode);
			}
			if (type.equals(NodeType.BEER)) {
				connectBeerNodeToIndex(newNode);
			}
			if (type.equals(NodeType.BEERTYPE)) {
				connectBeertypeNodeToIndex(newNode);
			}
			if (type.equals(NodeType.BREWERY)) {
				connectBreweryNodeToIndex(newNode);
			}
			if (type.equals(NodeType.BARCODE)) {
				connectBarcodeNodeToIndex(newNode);
			}
		} finally {
			transaction.finish();
		}
		return newNode;

	}

	private static void connectBreweryNodeToIndex(Node newNode) {
		final Node indexNode = getBreweryIndex();
		DBUtil.setProperty(newNode, NodeProperty.TYPE, NodeType.BREWERY);
		DBUtil.createRelationship(indexNode, RelType.INDEXES, newNode);
	}

	private static void connectBeertypeNodeToIndex(Node newNode) {
		final Node indexNode = getBeertypeIndex();
		DBUtil.setProperty(newNode, NodeProperty.TYPE, NodeType.BEERTYPE);
		DBUtil.createRelationship(indexNode, RelType.INDEXES, newNode);
	}

	private static void connectBarcodeNodeToIndex(Node newNode) {
		final Node indexNode = getBarcodeIndex();
		DBUtil.setProperty(newNode, NodeProperty.TYPE, NodeType.BARCODE);
		DBUtil.createRelationship(indexNode, RelType.INDEXES, newNode);
	}

	private static Node getBreweryIndex() {
		return Cypher.executeAndGetSingleNode(Cypherqueries.GET_BREWERY_INDEX_NODE, "BREWERY_INDEX");
	}

	private static Node getBeertypeIndex() {
		return Cypher.executeAndGetSingleNode(Cypherqueries.GET_BEERTYPE_INDEX_NODE, "BEERTYPE_INDEX");
	}

	private static Node getBarcodeIndex() {
		return Cypher.executeAndGetSingleNode(Cypherqueries.GET_BARCODE_INDEX_NODE, "BARCODE_INDEX");
	}

	/**
	 * Adds or updates the given property with the given value on the given node.
	 * 
	 * @param node
	 *            Node on which the property is set.
	 * @param key
	 *            Key of the Property
	 * @param value
	 *            Value of the Property,
	 */
	public static void setProperty(Node node, String key, Object value) {
		final Transaction transaction = DB.beginTx();
		try {
			node.setProperty(key, value);
			transaction.success();
		} finally {
			transaction.finish();
		}
	}

	/**
	 * Checks whether a usernode is existing or not.
	 * 
	 * @param username
	 *            Username of the user to be checked
	 * @return True if the user node exists, false otherwise
	 */
	public static boolean doesUserExist(String username) {
		return DBUtil.getUserByName(username) != null;
	}

	/**
	 * @param username
	 *            Username used to filter the timeline.
	 * @return Chronological list of actions filtered by Username.
	 * @param maxNumberOfItems
	 *            number of max. Items (actions) returned. Pass 0 for all Items.
	 * @param skipCount
	 *            Number of elements to be skipped for paging.
	 */
	public static List<Node> getTimeLineForUser(String username, int maxNumberOfItems, int skipCount) {
		if (maxNumberOfItems <= 0) {
			return Cypher.executeAndGetNodes(Cypherqueries.GET_TIMELINE_FOR_USER, "Action", 0, username);
		} else {
			return Cypher.executeAndGetNodes(Cypherqueries.GET_TIMELINE_FOR_USER, "Action", maxNumberOfItems, skipCount, username);
		}
	}

	/**
	 * @param maxNumberOfItems
	 *            number of max. Items (actions) returned. Pass 0 for all Items.
	 * @param skipCount
	 *            Number of Elements to be skipped for paging.
	 * @return Chronological list of all actions (Ratings and Consumptions).
	 */
	public static List<Node> getTimeLine(int maxNumberOfItems, int skipCount) {
		if (maxNumberOfItems <= 0) {
			return Cypher.executeAndGetNodes(Cypherqueries.GET_TIMELINE, "Action");
		} else {
			return Cypher.executeAndGetNodes(Cypherqueries.GET_TIMELINE, "Action", maxNumberOfItems, skipCount);
		}
	}

	/**
	 * Adds a new Action to the Timeline.
	 * 
	 * @param node
	 *            the action node to be added.
	 */
	public static void addToTimeLine(Node node) {
		final Node home = DB.getReferenceNode();
		final Node timeLineStart = home.getSingleRelationship(RelType.INDEX_TIMELINESTART, Direction.OUTGOING).getEndNode();
		final Relationship relationToNext = timeLineStart.getSingleRelationship(RelType.NEXT, Direction.OUTGOING);
		final Node next = relationToNext.getEndNode();
		final Transaction tx = DB.beginTx();
		try {
			relationToNext.delete();
			timeLineStart.createRelationshipTo(node, RelType.NEXT);
			node.createRelationshipTo(next, RelType.NEXT);
			tx.success();
		} finally {
			tx.finish();
		}
	}

	/**
	 * @param beerId
	 *            ID of the beer.
	 * @param username
	 *            Username of the user.
	 * @return The most recent rating for the given beer of the given user.
	 */
	public static Node getActiveUserRatingForBeer(long beerId, String username) {
		final Node activeRating = Cypher.executeAndGetSingleNode(Cypherqueries.GET_ACTIVE_RATING, "Rating", Long.toString(beerId), username);
		return activeRating;
	}

	/**
	 * @param node
	 *            Rating node.
	 * @param beerModel
	 *            BeerModel rated by the rating
	 * @param userModel
	 *            UserModel that rates the beer.
	 */
	public static void addToRatingIndex(Node node, BeerModel beerModel, UserModel userModel) {
		final Node home = DB.getReferenceNode();
		final Transaction tx = DB.beginTx();
		try {
			final Node activeRatingIndex = home.getSingleRelationship(RelType.INDEX_ACTIVERATINGINDEX, Direction.OUTGOING).getEndNode();
			final Node activeRating = getActiveUserRatingForBeer(beerModel.getId(), userModel.getUsername());
			if (activeRating != null) {
				final Relationship rel = activeRating.getSingleRelationship(RelType.INDEXES_ACTIVE, Direction.INCOMING);
				rel.delete();
			}
			activeRatingIndex.createRelationshipTo(node, RelType.INDEXES_ACTIVE);
			tx.success();
		} finally {
			tx.finish();
		}

	}

	/**
	 * Gets a list of all consuptions nodes for the given beer.
	 * 
	 * @param beerId
	 *            The id of the beer
	 * @return List of consumption nodes
	 */
	public static List<Node> getConsumptionsByBeer(long beerId) {
		return Cypher.executeAndGetNodes(Cypherqueries.GET_ALL_CONSUMPTIONS_FOR_BEER, "consumption", Long.toString(beerId));
	}

	/**
	 * Gets a list of consumption nodes for the user and the beer. The result is a list of all consumptions of a user
	 * for the given beer.
	 * 
	 * @param username
	 *            The user
	 * @param beerId
	 *            The beer
	 * @return The node list
	 */
	public static List<Node> getConsumptionsForUserByBeer(String username, long beerId) {
		return Cypher.executeAndGetNodes(Cypherqueries.GET_ALL_BEER_CONSUMPTIONS_FOR_USER, "consumption", Long.toString(beerId), username);
	}

	public static List<String> getAllBrands() {
		return Cypher.executeAndGetStrings(Cypherqueries.GET_ALL_BRANDS, "Brand");
	}
	
	/**
	 * Gets the "Unknown-Node" for a specific type.
	 * @param type Nodetype
	 * @return The "Unknown-Node"
	 */
	public static Node getUnknownNode(String type) {
		return Cypher.executeAndGetSingleNode(Cypherqueries.GET_UNKNOWN_NODE, "unknown", type);
	}

	public static Node getNodeByBarcode(String barcode) {
		return Cypher.executeAndGetSingleNode(Cypherqueries.GET_BARCODE_NODE, "barcode", barcode);
	}
}
