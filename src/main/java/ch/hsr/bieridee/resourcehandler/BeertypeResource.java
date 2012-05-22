package ch.hsr.bieridee.resourcehandler;


import org.apache.commons.lang.NotImplementedException;
import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BeertypeModel;
import ch.hsr.bieridee.resourcehandler.interfaces.IDocumentResource;

/**
 * Beer resource.
 * 
 */
public class BeertypeResource extends ServerResource implements IDocumentResource {
	
	private long beertypeId;
	
	@Override
	public void doInit() {
		this.beertypeId = Long.parseLong((String) (getRequestAttributes().get(Res.BEERTYPE_REQ_ATTR)));
	}
	
	@Override
	public Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException {
		
		final BeertypeModel beertypeModel =  new BeertypeModel(this.beertypeId);
		
		final JacksonRepresentation<BeertypeModel> beertypeJacksonRep = new JacksonRepresentation<BeertypeModel>(beertypeModel);
		beertypeJacksonRep.setObjectMapper(Config.getObjectMapper());
		
		return beertypeJacksonRep;
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