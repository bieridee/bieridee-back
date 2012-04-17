package ch.hsr.bieridee.resourcehandler.interfaces;

import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;

import ch.hsr.bieridee.exceptions.WrongNodeTypeException;

/**
 * Interface for the beer server resource.
 * 
 * @author cfaessle
 *
 */
public interface IBeerResource {

	/**
	 * Get a beer!
	 * 
	 * @return A Beer
	 * @throws NodeNotFoundException 
	 * @throws WrongNodeTypeException 
	 */
	@Get
	Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException;
	
}
