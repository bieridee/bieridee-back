package ch.hsr.bieridee.resourcehandler.interfaces;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;

/**
 * Interface for the beertype list resource.
 * 
 */
public interface IBeertypeListResource {
	
	/**
	 * Gets a list of beertypes.
	 * @return Representation of the beertype list
	 */
	@Get
	Representation retrieve();
	
}
