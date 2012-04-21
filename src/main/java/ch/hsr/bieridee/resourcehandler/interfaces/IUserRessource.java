package ch.hsr.bieridee.resourcehandler.interfaces;

import java.io.IOException;

import org.json.JSONException;
import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

import ch.hsr.bieridee.exceptions.WrongNodeTypeException;

/**
 * Interface for the User resource.
 */
public interface IUserRessource {

	/**
	 * Gets a User.
	 * 
	 * @throws WrongNodeTypeException
	 *             Thrown if the handled node has the wrong type
	 * @throws NodeNotFoundException
	 *             Thrown if the handled node is not existant
	 * @return Representation of a user
	 */
	@Get
	Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException;

	/**
	 * Stores or updates a user.
	 * 
	 * @param user
	 *            The user as JSON representation
	 * @throws IOException 
	 * @throws JSONException 
	 */
	@Put
	void store(Representation user) throws JSONException, IOException;

}
