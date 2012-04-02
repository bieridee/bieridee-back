package ch.hsr.bieridee.utils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Node;

import ch.hsr.bieridee.Main;

/**
 * @author chrigi Class containg all the cipher queries.
 */
public final class Cypher {

	private Cypher() {

	}

	/**
	 * @param query
	 *            String containg a Cypher query.
	 * @param column
	 *            Column Name containing the Nodes that will be returned.
	 * @param targetObject
	 * @return
	 */
	/**
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
	 * @param query
	 * @param column
	 * @param param
	 * @return
	 */
	public static List<Node> executeAndGetNodes(String query, String column, String param) {
		return Cypher.executeAndGetNodes(query.replace("$1", param), column);
	}

	/**
	 * @param query
	 * @param column
	 * @return
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
}
