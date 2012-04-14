package ch.hsr.bieridee.resourcehandler.interfaces;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;

/**
 * Interface for the beertype resource.
 *
 */
public interface IBeertypeResource {
	
	/**
	 * Gets a Beertype.
	 * 
	 * @return The representation of a beertype
	 */
	@Get
	Representation retrieve();	
	
}
