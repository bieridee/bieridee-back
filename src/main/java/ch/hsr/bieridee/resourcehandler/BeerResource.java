package ch.hsr.bieridee.resourcehandler;

import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.domain.Beer;
import ch.hsr.bieridee.models.BeerModel;
import ch.hsr.bieridee.resourcehandler.interfaces.IBeerResource;

/**
 * Beer resource.
 * 
 * @author cfaessle, jfurrer
 *
 */
public class BeerResource extends ServerResource implements IBeerResource {

	@Override
	public Beer retrieve() {
		final BeerModel bm = new BeerModel(5);
		return bm.getDomainObject();
	}

}