package ch.hsr.bieridee.resourcehandler.interfaces;

import java.io.IOException;

import org.json.JSONException;
import org.neo4j.graphdb.NotFoundException;
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
	 * @param rep The information for the new/update document
	 * @return The created or updated document
	 * @throws JSONException
	 * @throws IOException
	 * @throws NotFoundException
	 * @throws WrongNodeTypeException
	 */
	@Put
	Representation store(Representation rep) throws JSONException, IOException, NotFoundException, WrongNodeTypeException;
	
	/**
	 * Delete a document.
	 * @throws WrongNodeTypeException 
	 * @throws NotFoundException 
	 */
	@Delete
	void remove() throws NotFoundException, WrongNodeTypeException;
}