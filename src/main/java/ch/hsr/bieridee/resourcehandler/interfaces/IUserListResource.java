package ch.hsr.bieridee.resourcehandler.interfaces;

import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;

import ch.hsr.bieridee.exceptions.WrongNodeTypeException;

/**
 * Interface for the user list resource.
 * 
 */
public interface IUserListResource {

	/**
	 * Gets a list of users.
	 * 
	 * @return Representation of the user list
	 * @throws NodeNotFoundException 
	 * @throws WrongNodeTypeException 
	 */
	@Get
	Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException;

}
