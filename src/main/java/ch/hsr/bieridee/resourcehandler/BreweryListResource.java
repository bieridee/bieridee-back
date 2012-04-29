package ch.hsr.bieridee.resourcehandler;

import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BreweryModel;
import ch.hsr.bieridee.resourcehandler.interfaces.ICollectionResource;

/**
 * ServerResource for getting a List of Breweries.
 */
public class BreweryListResource extends ServerResource implements ICollectionResource {

	@Override
	public Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException {
		List<BreweryModel> breweryModels;

		// Process filter parameters, get nodes
		final String brewerySize = getQuery().getFirstValue(Res.BREWERY_FILTER_PARAMETER_SIZE);
		if (brewerySize != null) {
			breweryModels = BreweryModel.getAll(brewerySize);
		} else {
			breweryModels = BreweryModel.getAll();
		}
		
		final BreweryModel[] breweryModelArray = breweryModels.toArray(new BreweryModel[breweryModels.size()]);

		// Jackson representation
		final JacksonRepresentation<BreweryModel[]> breweryArrayJacksonRep = new JacksonRepresentation<BreweryModel[]>(breweryModelArray);
		breweryArrayJacksonRep.setObjectMapper(Config.getObjectMapper());

		return breweryArrayJacksonRep;
	}


	@Override
	public void store(Representation rep) {
		throw new NotImplementedException(); // TODO
	}

}

