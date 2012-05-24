package ch.hsr.bieridee.resourcehandler.interfaces;

import java.io.IOException;

import org.json.JSONException;
import org.neo4j.graphdb.NotFoundException;
import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import ch.hsr.bieridee.exceptions.InvalidRequestException;
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
	 * @return A representation of the newly created object
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws WrongNodeTypeException 
	 * @throws NotFoundException 
	 * @throws InvalidRequestException 
	 */
	@Post
	Representation store(Representation rep) throws JSONException, IOException, NotFoundException, WrongNodeTypeException, InvalidRequestException;
}
