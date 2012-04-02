package ch.hsr.bieridee.resourcehandler.interfaces;

import org.restlet.resource.Get;

/**
 * Interface for the User resource.
 * 
 * @author cfaessle
 * 
 */
public interface IUserRessource {

	/**
	 * Gets a User.
	 * 
	 * @return A User
	 */
	@Get
	String retrieve();

}
