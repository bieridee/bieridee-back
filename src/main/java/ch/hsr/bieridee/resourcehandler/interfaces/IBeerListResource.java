package ch.hsr.bieridee.resourcehandler.interfaces;

import java.util.List;

import org.restlet.resource.Get;

import ch.hsr.bieridee.domain.Beer;

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
	List<Beer> retrieve();

}
