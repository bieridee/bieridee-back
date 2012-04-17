package ch.hsr.bieridee.resourcehandler.interfaces;

import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.Delete;

import ch.hsr.bieridee.exceptions.WrongNodeTypeException;

/**
 * Represents a document resource.
 */
public interface IDocumentResource {
	/**
	 * Retrieve a document.
	 * @return The document representation.
	 * @throws NodeNotFoundException 
	 * @throws WrongNodeTypeException 
	 */
	@Get
	Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException;
	
	/**
	 * Update or create a document.
	 */
	@Put
	void store();
	
	/**
	 * Delete a document.
	 */
	@Delete
	void remove();
}