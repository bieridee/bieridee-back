package ch.hsr.bieridee.resourcehandler.interfaces;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;

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
	 */
	@Get
	Representation retrieve();
	
}
