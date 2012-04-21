package ch.hsr.bieridee.resourcehandler.interfaces;

import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;

import ch.hsr.bieridee.exceptions.WrongNodeTypeException;

/**
<<<<<<< HEAD
 * Interface for the BeerList server resource.
=======
 * Represents a read only resource (might be a document or collection).
>>>>>>> origin/resource_interfaces_generalization
 */
public interface IReadOnlyResource {
	/**
	 * Retrieve a read only resource.
	 * @return The resource representation.
	 * @throws NodeNotFoundException 
	 * @throws WrongNodeTypeException 
	 */
	@Get
	Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException;
}
