package ch.hsr.bieridee.resourcehandler.interfaces;

import org.restlet.resource.Get;

import ch.hsr.bieridee.domain.Beer;

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
	Beer retrieve();
	
}
