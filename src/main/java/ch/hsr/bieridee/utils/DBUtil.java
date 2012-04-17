package ch.hsr.bieridee.utils;

import java.util.List;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import ch.hsr.bieridee.Main;

/**
 * Utility to work with the neo4J graph-db.
 */
public final class DBUtil {

	private static EmbeddedGraphDatabase DB;

	private DBUtil() {
		// do not instanciate
	}

	/**
	 * Gets the neo4j node by the absolute node id.
	 * 
	 * @param id
	 *            The id of the neo4j node
	 * @return The node with the given id
	 */
	public static Node getNodeById(long id) {
		DB = Main.getGraphDb();
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
	 * Gets a list of all beer nodes form the database.
	 * 
	 * @return List ob all existing beernodes.
	 */
	public static List<Node> getBeerNodeList() {
		return Cypher.executeAndGetNodes(Cypherqueries.GET_ALL_BEERS, "Beer");
	}

	/**
	 * Gets a list of all beertype nodes form the database.
	 * 
	 * @return List of all existing beertype nodes.
	 */
	public static List<Node> getBeertypeNodeList() {
		return Cypher.executeAndGetNodes(Cypherqueries.GET_ALL_BEERTYPES, "Beertype");
	}

	/**
	 * Gets a list of all user nodes form the database.
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

}
