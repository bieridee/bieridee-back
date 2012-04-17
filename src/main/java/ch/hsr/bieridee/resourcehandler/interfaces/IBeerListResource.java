package ch.hsr.bieridee.resourcehandler.interfaces;

import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;

import ch.hsr.bieridee.exceptions.WrongNodeTypeException;

/**
 * Interface for the BeerList server resource.
 * 
 * @author cfaessle
 *
 */
public interface IBeerListResource {

	/**
	 * Retrieve a list of Beer.
	 * @return A List of beer
	 * @throws NodeNotFoundException 
	 * @throws WrongNodeTypeException 
	 */
	@Get
	Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException;

}
