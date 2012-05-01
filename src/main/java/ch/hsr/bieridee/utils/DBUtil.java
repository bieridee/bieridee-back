package ch.hsr.bieridee.utils;

import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;

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
	 * @param tagName
	 *            value of a Tag which is used as a filter.
	 * @return a List of Beers matching the given Tag name.
	 */
	public static List<Node> getBeerNodeList(String tagName) {
		return Cypher.executeAndGetNodes(Cypherqueries.GET_BEERS_BY_TAG_NAME, "Beer", tagName);
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
		} finally {
			transaction.finish();
		}
		return newNode;

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
	 * @param maxNumberOfItems
	 *            number of max. Items (actions) returned. Pass 0 for all Items.
	 * @return Chronological list of all actions (Ratings and Consumptions).
	 */
	public static List<Node> getTimeLine(int maxNumberOfItems) {
		if (maxNumberOfItems <= 0) {
			return Cypher.executeAndGetNodes(Cypherqueries.GET_TIMELINE, "Action");
		} else {
			return Cypher.executeAndGetNodes(Cypherqueries.GET_TIMELINE, "Action", maxNumberOfItems);
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
				System.out.println("active Rating "+activeRating);
				final Relationship rel = activeRating.getSingleRelationship(RelType.INDEXES_ACTIVE, Direction.INCOMING);
				System.out.println("Relationship "+rel);
				rel.delete();
			}
			activeRatingIndex.createRelationshipTo(node, RelType.INDEXES_ACTIVE);
			tx.success();
		} finally {
			tx.finish();
		}

	}

}
