package ch.hsr.bieridee.resourcehandler;


import org.apache.log4j.Logger;
import org.restlet.data.Status;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.domain.Beertype;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BeertypeModel;
import ch.hsr.bieridee.resourcehandler.interfaces.IBeertypeResource;

/**
 * Beer resource.
 * 
 */
public class BeertypeResource extends ServerResource implements IBeertypeResource {
	
	private static final Logger LOG = Logger.getLogger(BeertypeResource.class);
	private long beertypeId;
	
	@Override
	public void doInit() {
		this.beertypeId = Long.parseLong((String) (getRequestAttributes().get(Res.BEERTYPE_REQ_ATTR)));
	}
	
	@Override
	public Representation retrieve() {
		
		BeertypeModel btm = null;
		
		try {
			btm = new BeertypeModel(this.beertypeId);
		} catch (WrongNodeTypeException e) {
			LOG.warn(e.getMessage(), e);
			setStatus(Status.CLIENT_ERROR_NOT_FOUND, e, e.getMessage());
			return null;
		}
		
		final Beertype beertype = btm.getDomainObject();
		
		final JacksonRepresentation<Beertype> beertypeJacksonRep = new JacksonRepresentation<Beertype>(beertype);
		beertypeJacksonRep.setObjectMapper(Config.getObjectMapper());
		
		return beertypeJacksonRep;
	}

}