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
import ch.hsr.bieridee.models.BeerModel;
import ch.hsr.bieridee.resourcehandler.interfaces.ICollectionResource;

/**
 * ServerResource for getting a List of Beers.
 */
public class BeerListResource extends ServerResource implements ICollectionResource {

	@Override
	public Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException {
		
		List<BeerModel> beerModels;
		
		if (getQuery().isEmpty()) {
			beerModels = BeerModel.getAll();
		} else {
			final long tagId = Long.parseLong(getQuery().getFirstValue(Res.BEER_FILTER_PARAMETER_TAG));
			beerModels = BeerModel.getAll(tagId);
		}
		
		final BeerModel[] beerModelArray = beerModels.toArray(new BeerModel[beerModels.size()]);

		// json representation
		final JacksonRepresentation<BeerModel[]> beerArrayJacksonRep = new JacksonRepresentation<BeerModel[]>(beerModelArray);
		beerArrayJacksonRep.setObjectMapper(Config.getObjectMapper());

		return beerArrayJacksonRep;
	}


	@Override
	public void store(Representation rep) {
		throw new NotImplementedException(); // TODO
	}

}

