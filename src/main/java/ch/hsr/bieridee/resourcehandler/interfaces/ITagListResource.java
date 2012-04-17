package ch.hsr.bieridee.resourcehandler.interfaces;

import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;

import ch.hsr.bieridee.exceptions.WrongNodeTypeException;

/**
 * Interface for the tag list resource.
 *
 */
public interface ITagListResource {
	
	/**
	 * Gets a list of tags.
	 * @return Representation of the tag list
	 * @throws NodeNotFoundException 
	 * @throws WrongNodeTypeException 
	 */
	@Get
	Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException;
}
