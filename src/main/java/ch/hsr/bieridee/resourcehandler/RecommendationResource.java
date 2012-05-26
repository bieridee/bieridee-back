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
import ch.hsr.bieridee.models.RecommendationModel;
import ch.hsr.bieridee.models.UserModel;
import ch.hsr.bieridee.resourcehandler.interfaces.IReadOnlyResource;
import ch.hsr.bieridee.utils.DBUtil;
import ch.hsr.bieridee.utils.Recommendator;

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
		final UserModel userModel = new UserModel(this.username);
		
		final Recommendator recommendator = new Recommendator(userModel);
		final List<RecommendationModel> recommendations = recommendator.calulateRecommendations();
		
		final JacksonRepresentation<RecommendationModel[]> recomRep = new JacksonRepresentation<RecommendationModel[]>(recommendations.toArray(new RecommendationModel[recommendations.size()]));
		recomRep.setObjectMapper(Config.getObjectMapper());

		return recomRep;
	}

}
