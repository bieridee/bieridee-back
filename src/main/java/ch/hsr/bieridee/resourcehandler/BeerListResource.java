package ch.hsr.bieridee.resourcehandler;

import java.util.List;

import org.neo4j.graphdb.Node;
import org.restlet.data.Status;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.domain.Beer;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
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
		List<BeerModel> beerModels = null;
		try {
			beerModels = DomainConverter.createBeerModelsFromList(beerNodes);
		} catch (WrongNodeTypeException e) {
			setStatus(Status.CLIENT_ERROR_NOT_FOUND, e, e.getMessage());
			return null;
		}
		final List<Beer> beerList = DomainConverter.extractDomainObjectFromModel(beerModels);
		final Beer[] beers = beerList.toArray(new Beer[beerList.size()]);
		
		// json representation
		final JacksonRepresentation<Beer[]> beerArrayJacksonRep = new JacksonRepresentation<Beer[]>(beers);
		beerArrayJacksonRep.setObjectMapper(Config.getObjectMapper());

		return beerArrayJacksonRep;
	}

}