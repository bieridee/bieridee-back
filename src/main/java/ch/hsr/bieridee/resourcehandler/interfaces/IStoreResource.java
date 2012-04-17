package ch.hsr.bieridee.resourcehandler.interfaces;

import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

import ch.hsr.bieridee.exceptions.WrongNodeTypeException;

/**
 * Represents a store resource.
 */
public interface IStoreResource {
	/**
	 * Retrieve a store item.
	 * @return The store item representation.
	 * @throws NodeNotFoundException 
	 * @throws WrongNodeTypeException 
	 */
	@Get
	Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException;
	
	/**
	 * Update or create a store item.
	 * @param rep Representation of the store item to store.
	 */
	@Put
	void store(Representation rep);
	
	/**
	 * Delete a store item.
	 * @param rep Representation of the store item to delete.
	 */
	@Delete
	void remove(Representation rep);
}
