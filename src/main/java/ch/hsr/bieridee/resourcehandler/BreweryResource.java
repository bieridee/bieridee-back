package ch.hsr.bieridee.resourcehandler;


import org.apache.commons.lang.NotImplementedException;
import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BreweryModel;
import ch.hsr.bieridee.resourcehandler.interfaces.IDocumentResource;

/**
 * Brewery resource.
 * 
 */
public class BreweryResource extends ServerResource implements IDocumentResource {
	
	private long breweryId;
	
	@Override
	public void doInit() {
		this.breweryId = Long.parseLong((String) (getRequestAttributes().get(Res.BREWERY_REQ_ATTR)));
	}
	
	@Override
	public Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException {
		
		final BreweryModel breweryModel = new BreweryModel(this.breweryId);
		
		final JacksonRepresentation<BreweryModel> beerJacksonRep = new JacksonRepresentation<BreweryModel>(breweryModel);
		beerJacksonRep.setObjectMapper(Config.getObjectMapper());
		
		return beerJacksonRep;
	}

	@Override
	public Representation store(Representation rep) {
		throw new NotImplementedException(); // TODO
	}

	@Override
	public void remove() {
		throw new NotImplementedException(); // TODO
	}

}