package ch.hsr.bieridee.utils;

import java.util.LinkedList;
import java.util.List;

import org.neo4j.graphdb.Node;

import ch.hsr.bieridee.models.AbstractModel;
import ch.hsr.bieridee.models.BeerModel;
import ch.hsr.bieridee.models.BeertypeModel;

/**
 * Utility Class providing methods to do mass wrapping of nodes with Model Objects.
 * 
 * @author cfaessle
 * 
 */
public final class DomainConverter {

	private DomainConverter() {
		// do not instanciate
	}
	
	/**
	 * Converts a list of beer nodes into a list of BeerModels.
	 * 
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
	 * Converts a list of beertype nodes into a list of BeertypeModels.
	 * 
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

	/**
	 * Extracts the beer domainobjects form a list of BeerModels.
	 * 
	 * @param models
	 *            The AbstractModel list
	 * 
	 * @param <T>
	 *            Type of the Domain Object
	 * @return A List of Beers
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> extractDomainObjectFromModel(List<? extends AbstractModel> models) {
		final List<T> domainObjects = new LinkedList<T>();
		for (AbstractModel m : models) {
			domainObjects.add((T) m.getDomainObject());
		}
		return domainObjects;
	}
}
