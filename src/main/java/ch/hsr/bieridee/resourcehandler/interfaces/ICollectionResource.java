package ch.hsr.bieridee.resourcehandler.interfaces;

import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import ch.hsr.bieridee.exceptions.WrongNodeTypeException;

/**
 * Represents a collection resource.
 */
public interface ICollectionResource {
	/**
	 * Retrieve a collection.
	 * @return The collection representation.
	 * @throws NodeNotFoundException 
	 * @throws WrongNodeTypeException 
	 */
	@Get
	Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException;
	
	/**
	 * Create a collection item.
	 * @param rep Representation of the collection item to create.
	 */
	@Post
	void store(Representation rep);
}
