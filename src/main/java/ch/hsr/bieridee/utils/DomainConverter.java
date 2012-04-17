package ch.hsr.bieridee.utils;

import java.util.LinkedList;
import java.util.List;

import org.neo4j.graphdb.Node;
import org.neo4j.server.rest.web.NodeNotFoundException;

import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.AbstractModel;
import ch.hsr.bieridee.models.BeerModel;
import ch.hsr.bieridee.models.BeertypeModel;
import ch.hsr.bieridee.models.TagModel;
import ch.hsr.bieridee.models.UserModel;

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
	 * @throws WrongNodeTypeException Thrown if one of the given nodes is not of type beer
	 * @throws NodeNotFoundException Thrown if one of the given nodes is not existing
	 */
	public static List<BeerModel> createBeerModelsFromList(List<Node> nodes) throws WrongNodeTypeException, NodeNotFoundException {
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
	 * @throws WrongNodeTypeException Thrown if one of the given nodes is not of type beertype
	 * @throws NodeNotFoundException Thrown if one of the given nodes is not existing
	 */
	public static List<BeertypeModel> createBeertypeModelsFromList(List<Node> nodes) throws WrongNodeTypeException, NodeNotFoundException {
		final List<BeertypeModel> models = new LinkedList<BeertypeModel>();
		for (Node n : nodes) {
			models.add(new BeertypeModel(n));
		}
		return models;
	}
	
	/**
	 * Converts a list of tag nodes into a list of TagModels.
	 * 
	 * @param nodes
	 *            <code>List</code> containing the Node Objects which will be wrapped with a TagModel.
	 * @return A list of <code>TagModel</code> Objects.
	 * @throws WrongNodeTypeException Thrown if one of the given nodes is not of type tag
	 * @throws NodeNotFoundException Thrown if one of the given nodes does not exist
	 */
	public static List<TagModel> createTagModelsFromList(List<Node> nodes) throws WrongNodeTypeException, NodeNotFoundException {
		final List<TagModel> models = new LinkedList<TagModel>();
		for (Node n : nodes) {
			models.add(new TagModel(n));
		}
		return models;
	}
	
	/**
	 * Converts a list of user nodes into a list of UserModels.
	 * 
	 * @param nodes
	 *            <code>List</code> containing the Node Objects which will be wrapped with a UserModel.
	 * @return A list of <code>UserModel</code> Objects.
	 * @throws WrongNodeTypeException Thrown if one of the given nodes is not of type user
	 * @throws NodeNotFoundException Thrown if one of the given nodes does not exist/is null
	 */
	public static List<UserModel> createUserModelsFromList(List<Node> nodes) throws WrongNodeTypeException, NodeNotFoundException {
		final List<UserModel> models = new LinkedList<UserModel>();
		for (Node n : nodes) {
			models.add(new UserModel(n));
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
