package ch.hsr.bieridee.resourcehandler;

import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.domain.BrewerySize;
import org.neo4j.graphdb.NotFoundException;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.resourcehandler.interfaces.IReadOnlyResource;

/**
 * Resourcehandler returning legal brewerysizes.
 * 
 */
public class BrewerySizeResource extends ServerResource implements IReadOnlyResource {
	@Override
	public Representation retrieve() throws WrongNodeTypeException, NotFoundException {
		final JacksonRepresentation<String[]> jacksonRep = new JacksonRepresentation<String[]>(BrewerySize.SIZES);
		jacksonRep.setObjectMapper(Config.getObjectMapper());
		return jacksonRep;
	}

}
