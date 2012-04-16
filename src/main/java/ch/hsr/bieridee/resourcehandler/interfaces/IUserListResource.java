package ch.hsr.bieridee.resourcehandler.interfaces;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;

/**
 * Interface for the user list resource.
 * 
 */
public interface IUserListResource {

	/**
	 * Gets a list of users.
	 * 
	 * @return Representation of the user list
	 */
	@Get
	Representation retrieve();

}
