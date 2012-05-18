package ch.hsr.bieridee.resourcehandler;

import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.resourcehandler.interfaces.IReadOnlyResource;

/**
 * Resourcehandler returning legal brewerysizes.
 * 
 */
public class BrewerySizeResource extends ServerResource implements IReadOnlyResource {
	public static final String[] BREWERYSIZES = { "Size-S", "Size-M", "Size-L" };

	@Override
	public Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException {
		final JacksonRepresentation<String[]> jacksonRep = new JacksonRepresentation<String[]>(BREWERYSIZES);
		jacksonRep.setObjectMapper(Config.getObjectMapper());
		return jacksonRep;
	}

}
