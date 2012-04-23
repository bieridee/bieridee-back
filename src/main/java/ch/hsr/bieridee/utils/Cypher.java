package ch.hsr.bieridee.utils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Node;

import ch.hsr.bieridee.Main;

/**
 * Class containg all the cipher queries.
 */
public final class Cypher {

	private Cypher() {
		// do not instantiate.
	}

	/**
	 * Returns the nodes found by the given cypher-query as a list.
	 * 
	 * @param query
	 *            The Cypher query.
	 * @param column
	 *            Column name containing the Nodes that will be returned.
	 * @return A List containing the nodes returned by the query.
	 */
	public static List<Node> executeAndGetNodes(String query, String column) {
		final ExecutionEngine engine = new ExecutionEngine(Main.getGraphDb());
		final ExecutionResult result = (ExecutionResult) engine.execute(query);
		final Iterator<Node> iterator = result.columnAs(column);
		final List<Node> resultNodes = new LinkedList<Node>();
		while (iterator.hasNext()) {
			resultNodes.add(iterator.next());
		}
		return resultNodes;
	}

	/**
	 * Returns the nodes found by the given cypher-query as a list.
	 * 
	 * @param query
	 *            The cypher query
	 * @param column
	 *            Column name containing the Nodes that will be returned.
	 * @param param
	 *            The parameter will be used as a replacement for the first occurence of the literal '$1' in the cypher
	 *            query
	 * @return A List containing the nodes returned by the query.
	 */
	public static List<Node> executeAndGetNodes(String query, String column, String param) {
		return executeAndGetNodes(query.replace("$1", param), column);
	}

	/**
	 * @param query
	 *            The cypher query
	 * @param column
	 *            Column name containing the Nodes that will be returned
	 * @param param
	 *            The parameter will be used as a replacement for the first occurence of the literal '$1' in the cypher
	 * @param limit
	 *            Max. number of Nodes returned.
	 * @return A List containing the nodes returned by the query.
	 */
	public static List<Node> executeAndGetNodes(String query, String column, String param, int limit) {
		if (limit <= 0) {
			Cypher.executeAndGetNodes(query, column, param);
		}
		final String limitClause = " LIMIT " + limit;
		return Cypher.executeAndGetNodes(query + limitClause, column, param);

	}

	/**
	 * @param query
	 *            The cypher query
	 * @param column
	 *            Column name containing the Nodes that will be returned.
	 * @param limit
	 *            number of Nodes returned.
	 * @return A List containg the nodes returned by the query.
	 */
	public static List<Node> executeAndGetNodes(String query, String column, int limit) {
		final String limitClause = " LIMIT " + limit;
		return executeAndGetNodes(query + limitClause, column, limit);
	}

	/**
	 * Returns a single node found by the given cypher-query.
	 * 
	 * @param query
	 *            The cypher query
	 * @param column
	 *            Column name containing the Nodes that will be returned.
	 * @return The desired node or null if none found
	 */
	public static Node executeAndGetSingleNode(String query, String column) {
		final ExecutionEngine engine = new ExecutionEngine(Main.getGraphDb());
		final ExecutionResult result = (ExecutionResult) engine.execute(query);
		final Iterator<Node> iterator = result.columnAs(column);
		Node resultNode = null;
		if (iterator.hasNext()) {
			resultNode = iterator.next();
		}
		return resultNode;
	}

	/**
	 * Returns a single node found by the given cypher-query.
	 * 
	 * @param query
	 *            The cypher query
	 * @param column
	 *            Column name containing the Nodes that will be returned.
	 * 
	 * @param param
	 *            The parameter will be used as a replacement for the first occurence of the literal '$1' in the cypher
	 *            query
	 * 
	 * @return The desired node
	 */
	public static Node executeAndGetSingleNode(String query, String column, String param) {
		return Cypher.executeAndGetSingleNode(query.replace("$1", param), column);
	}

}
