package ch.hsr.bieridee.resourcehandler;


import org.apache.commons.lang.NotImplementedException;
import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BeerModel;
import ch.hsr.bieridee.resourcehandler.interfaces.IDocumentResource;

/**
 * Beer resource.
 */
public class BeerResource extends ServerResource implements IDocumentResource {
	
	private long beerId;
	
	@Override
	public void doInit() {
		this.beerId = Long.parseLong((String) (getRequestAttributes().get(Res.BEER_REQ_ATTR)));
	}
	
	@Override
	public Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException {
		
		final BeerModel beerModel = new BeerModel(this.beerId);
		
		// json representation
		final JacksonRepresentation<BeerModel> beerJacksonRep = new JacksonRepresentation<BeerModel>(beerModel);
		beerJacksonRep.setObjectMapper(Config.getObjectMapper());
		
		return beerJacksonRep;
	}


	@Override
	public void store(Representation rep) {
		throw new NotImplementedException(); // TODO
	}
	
	@Override
	public void remove(Representation rep) {
		throw new NotImplementedException(); // TODO
	}

}

