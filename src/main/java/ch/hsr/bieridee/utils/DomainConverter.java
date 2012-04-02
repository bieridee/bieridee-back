package ch.hsr.bieridee.utils;

import java.util.LinkedList;
import java.util.List;

import org.neo4j.graphdb.Node;

import ch.hsr.bieridee.models.BeerModel;
import ch.hsr.bieridee.models.BeertypeModel;

/**
 * @author chrigi Utility Class providing methods to do mass wrapping of nodes with Model Objects.
 * 
 */
public final class DomainConverter {
	private DomainConverter() {

	}

	/**
	 * @param nodes
	 *            <code>List</code> containing the Node Objects which will be wrapped with a BeerModel.
	 * @return A list of <code>BeerModel</code> Objects.
	 */
	public static List<BeerModel> createBeerModelsFromList(List<Node> nodes) {
		final List<BeerModel> models = new LinkedList<BeerModel>();
		for (Node n : nodes) {
			models.add(new BeerModel(n));
		}
		return models;
	}

	/**
	 * @param nodes
	 *            <code>List</code> containing the Node Objects which will be wrapped with a BeertypeModel.
	 * @return A list of <code>BeertypeModel</code> Objects.
	 */
	public static List<BeertypeModel> createBeertypeModelsFromList(List<Node> nodes) {
		final List<BeertypeModel> models = new LinkedList<BeertypeModel>();
		for (Node n : nodes) {
			models.add(new BeertypeModel(n));
		}
		return models;
	}
	
	
	
	
	
	public static<T> List<T> getList(){
		T a;
		
		
		return new LinkedList<T>();
	}

}
