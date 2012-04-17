package ch.hsr.bieridee.resourcehandler.interfaces;

import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;

import ch.hsr.bieridee.exceptions.WrongNodeTypeException;

/**
 * Interface for the beertype resource.
 *
 */
public interface IBeertypeResource {
	
	/**
	 * Gets a Beertype.
	 * 
	 * @return The representation of a beertype
	 * @throws NodeNotFoundException 
	 * @throws WrongNodeTypeException 
	 */
	@Get
	Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException;	
	
}
