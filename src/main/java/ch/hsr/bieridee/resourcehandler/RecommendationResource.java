package ch.hsr.bieridee.resourcehandler;

import java.util.List;

import org.neo4j.graphdb.NotFoundException;
import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BeerModel;
import ch.hsr.bieridee.resourcehandler.interfaces.IReadOnlyResource;
import ch.hsr.bieridee.utils.DBUtil;

/**
 * Resource returning a list of recommendet beers.
 * 
 */
public class RecommendationResource extends ServerResource implements IReadOnlyResource {

	private String username;

	@Override
	public void doInit() {
		this.username = (String) (getRequestAttributes().get(Res.USER_REQ_ATTR));
		if (!DBUtil.doesUserExist(this.username)) {
			throw new NotFoundException("Username invalid");
		}
	}

	@Override
	public Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException {
		System.out.println("calculating recommendations for user " + username);
		final List<BeerModel> ratedBeersByUser = this.getRatedBeersByUser(this.username);

		List<BeerModel> recommendetBeers = ratedBeersByUser;
		final BeerModel[] beerModelArray = recommendetBeers.toArray(new BeerModel[ratedBeersByUser.size()]);

		// json representation
		final JacksonRepresentation<BeerModel[]> beerArrayJacksonRep = new JacksonRepresentation<BeerModel[]>(beerModelArray);
		beerArrayJacksonRep.setObjectMapper(Config.getObjectMapper());

		return beerArrayJacksonRep;
	}

	public List<BeerModel> getRatedBeersByUser(String username) throws NotFoundException, WrongNodeTypeException {
		return BeerModel.createModelsFromNodes(DBUtil.getBeersRatedByUser(username, 10));
	}

}
