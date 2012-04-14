package ch.hsr.bieridee.resourcehandler;


import org.apache.log4j.Logger;
import org.restlet.data.Status;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.domain.Beer;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BeerModel;
import ch.hsr.bieridee.resourcehandler.interfaces.IBeerResource;

/**
 * Beer resource.
 * 
 * @author cfaessle, jfurrer
 *
 */
public class BeerResource extends ServerResource implements IBeerResource {
	
	private static final Logger LOG = Logger.getLogger(BeerResource.class);
	private long beerId;
	
	@Override
	public void doInit() {
		this.beerId = Long.parseLong((String) (getRequestAttributes().get(Res.BEER_REQ_ATTR)));
	}
	
	@Override
	public Representation retrieve() {
		
		BeerModel bm = null;
		try {
			bm = new BeerModel(this.beerId);
		} catch (WrongNodeTypeException e) {
			LOG.warn(e.getMessage(), e);
			setStatus(Status.CLIENT_ERROR_NOT_FOUND, e, e.getMessage());
			return null;
		}
		
		final Beer b = bm.getDomainObject();
		
		// json representation
		final JacksonRepresentation<Beer> beerJacksonRep = new JacksonRepresentation<Beer>(b);
		beerJacksonRep.setObjectMapper(Config.getObjectMapper());
		
		return beerJacksonRep;
	}

}