package ch.hsr.bieridee.resourcehandler;

import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Config;
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
	public Representation retrieve() {
		final BeerModel bm = new BeerModel(5);
		final Beer b = bm.getDomainObject();
		
		// json representation
		final JacksonRepresentation<Beer> beerJacksonRep = new JacksonRepresentation<Beer>(b);
		beerJacksonRep.setObjectMapper(Config.getObjectMapper());
		
		return beerJacksonRep;
	}

}