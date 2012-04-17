package ch.hsr.bieridee.resourcehandler;

import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.neo4j.graphdb.Node;
import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.domain.Beer;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BeerModel;
import ch.hsr.bieridee.resourcehandler.interfaces.ICollectionResource;
import ch.hsr.bieridee.utils.DBUtil;
import ch.hsr.bieridee.utils.DomainConverter;

/**
 * ServerResource for getting a List of Beers.
 */
public class BeerListResource extends ServerResource implements ICollectionResource {

	@Override
	public Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException {

		final List<Node> beerNodes = DBUtil.getBeerNodeList();
		final List<BeerModel> beerModels = DomainConverter.createBeerModelsFromList(beerNodes);
		
		final List<Beer> beerList = DomainConverter.extractDomainObjectFromModel(beerModels);
		final Beer[] beers = beerList.toArray(new Beer[beerList.size()]);
		
		// json representation
		final JacksonRepresentation<Beer[]> beerArrayJacksonRep = new JacksonRepresentation<Beer[]>(beers);
		beerArrayJacksonRep.setObjectMapper(Config.getObjectMapper());

		return beerArrayJacksonRep;
	}

	@Override
	public void store(Representation rep) {
		throw new NotImplementedException(); // TODO
	}

}