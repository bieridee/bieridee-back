package ch.hsr.bieridee.resourcehandler.interfaces;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;

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
	 */
	@Get
	Representation retrieve();

}
