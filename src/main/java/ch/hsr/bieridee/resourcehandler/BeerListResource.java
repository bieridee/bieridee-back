package ch.hsr.bieridee.resourcehandler;

import java.util.List;

import org.neo4j.graphdb.Node;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.domain.Beer;
import ch.hsr.bieridee.models.BeerModel;
import ch.hsr.bieridee.resourcehandler.interfaces.IBeerListResource;
import ch.hsr.bieridee.utils.DBUtil;
import ch.hsr.bieridee.utils.DomainConverter;

/**
 * ServerResource for getting a List of Beers.
 * 
 * @author cfaessle, jfurrer
 */
public class BeerListResource extends ServerResource implements IBeerListResource {

	@Override
	public Representation retrieve() {

		final List<Node> beerNodes = DBUtil.getBeerNodeList();
		final List<BeerModel> beerModels = DomainConverter.createBeerModelsFromList(beerNodes);
		final List<Beer> beerList = DomainConverter.extractDomainObjectFromModel(beerModels);
		final Beer[] beers = beerList.toArray(new Beer[0]);
		
		// json representation
		final JacksonRepresentation<Beer[]> beerArrayJacksonRep = new JacksonRepresentation<Beer[]>(beers);
		beerArrayJacksonRep.setObjectMapper(Config.getObjectMapper());

		return beerArrayJacksonRep;
	}

}