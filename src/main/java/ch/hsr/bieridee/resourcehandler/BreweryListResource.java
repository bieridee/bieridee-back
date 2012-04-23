package ch.hsr.bieridee.resourcehandler;

import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.neo4j.graphdb.Node;
import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.domain.Beer;
import ch.hsr.bieridee.domain.Brewery;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BeerModel;
import ch.hsr.bieridee.models.BreweryModel;
import ch.hsr.bieridee.resourcehandler.interfaces.ICollectionResource;
import ch.hsr.bieridee.utils.DBUtil;
import ch.hsr.bieridee.utils.DomainConverter;

/**
 * ServerResource for getting a List of Breweries.
 */
public class BreweryListResource extends ServerResource implements ICollectionResource {

	@Override
	public Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException {
		List<Node> breweryNodes;

		// Process filter parameters, get nodes
		final String brewerySize = getQuery().getFirstValue(Res.BREWERY_FILTER_PARAMETER_SIZE);
		if (brewerySize != null) {
			breweryNodes = DBUtil.getBreweryNodeList(brewerySize);
		} else {
			breweryNodes = DBUtil.getBreweryNodeList();
		}

		// Convert nodes to list of models
		final List<BreweryModel> breweryModels = DomainConverter.createBreweryModelsFromList(breweryNodes);
		final List<Brewery> breweryList = DomainConverter.extractDomainObjectFromModel(breweryModels);
		final Brewery[] breweries = breweryList.toArray(new Brewery[breweryList.size()]);

		// Jackson representation
		final JacksonRepresentation<Brewery[]> breweryArrayJacksonRep = new JacksonRepresentation<Brewery[]>(breweries);
		breweryArrayJacksonRep.setObjectMapper(Config.getObjectMapper());

		return breweryArrayJacksonRep;
	}


	@Override
	public void store(Representation rep) {
		throw new NotImplementedException(); // TODO
	}

}

