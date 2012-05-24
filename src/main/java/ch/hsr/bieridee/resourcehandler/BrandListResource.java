package ch.hsr.bieridee.resourcehandler;

import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BrandModel;
import ch.hsr.bieridee.resourcehandler.interfaces.IReadOnlyResource;

/**
 * Resource to retrieve all brands.
 *
 */
public class BrandListResource extends ServerResource implements IReadOnlyResource {

	@Override
	public Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException {
		final String[] allBrands = BrandModel.getAll().toArray(new String[0]);
		final JacksonRepresentation<String[]> brandRep = new JacksonRepresentation<String[]>(allBrands);
		return brandRep;
	}

}
