package ch.hsr.bieridee.utils;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import ch.hsr.bieridee.Main;
import ch.hsr.bieridee.config.RelType;

/**
 * Utility to work with the neo4J graph-db.
 * 
 * @author jfurrer
 * 
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
		Transaction tx = null;
		Node node = null;
		try {
			tx = DB.beginTx();
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

	public static List<Node> getBeertypeNodeList() {
		return Cypher.executeAndGetNodes(Cypherqueries.GET_ALL_BEERTYPES, "Beertype");
	}

	public static List<Node> getUserNodeList() {
		return Cypher.executeAndGetNodes(Cypherqueries.GET_ALL_USERS, "User");

	}

	/**
	 * Gets a neo4j beer node by the name of the beer.
	 * 
	 * @param name
	 *            The name (name property) of the beer
	 * @return The beer with the given name or <code>null</code> if not found
	 */
	public static Node getBeerByName(String name) {
		return null;
	}

}
