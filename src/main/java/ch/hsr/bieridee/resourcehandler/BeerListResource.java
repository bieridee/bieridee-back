package ch.hsr.bieridee.resourcehandler;

import java.util.List;

import org.neo4j.graphdb.Node;
import org.restlet.resource.ServerResource;

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
	public List<Beer> retrieve() {

		final List<Node> beerNodes = DBUtil.getBeerNodeList();
		final List<BeerModel> beerModels= DomainConverter.createBeerModelsFromList(beerNodes);
		final List<Beer> beers = DomainConverter.extractDomainObjectFromModel(beerModels);
		
		return beers;
	}

}