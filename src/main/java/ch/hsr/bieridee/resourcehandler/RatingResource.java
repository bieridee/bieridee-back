package ch.hsr.bieridee.resourcehandler;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.graphdb.NotFoundException;
import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.data.MediaType;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BeerModel;
import ch.hsr.bieridee.models.RatingModel;
import ch.hsr.bieridee.models.UserModel;
import ch.hsr.bieridee.utils.NodeProperty;

/**
 * Server resource to provide access to users.
 */
public class RatingResource extends ServerResource {

	private String username;
	private long beerId;

	@Override
	public void doInit() {
		this.username = (String) getRequestAttributes().get(Res.USER_REQ_ATTR);
		this.beerId = Long.parseLong((String) (getRequestAttributes().get(Res.BEER_REQ_ATTR)));
	}

	/**
	 * Retrieve a rating.
	 * 
	 * @return Representation of the Rating
	 * @throws WrongNodeTypeException
	 *             Thrown if wrong node type
	 * @throws NodeNotFoundException
	 *             Thrown if a node was not found
	 */
	@Get
	public Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException {
		final RatingModel ratingModel = RatingModel.getCurrent(new BeerModel(this.beerId), new UserModel(this.username));
		final JacksonRepresentation<RatingModel> ratingRep = new JacksonRepresentation<RatingModel>(ratingModel);
		ratingRep.setObjectMapper(Config.getObjectMapper());
		return ratingRep;
	}

	/**
	 * Creates a new rating.
	 * 
	 * @param rating
	 *            The new raing
	 * @return The new average rating of the beer
	 * @throws JSONException
	 *             We got json problem
	 * @throws IOException
	 *             We got io problem
	 * @throws NotFoundException
	 *             We got node finding problem
	 * @throws WrongNodeTypeException
	 *             We got type problem
	 */
	@Post
	public Representation store(Representation rating) throws JSONException, IOException, NotFoundException, WrongNodeTypeException {

		final JSONObject ratingJSON = new JSONObject(rating.getText());
		final int ratingValue = ratingJSON.getInt(NodeProperty.Rating.RATING);
		final BeerModel beerModel = new BeerModel(this.beerId);
		final UserModel userModel = new UserModel(this.username);

		RatingModel.create(ratingValue, beerModel, userModel);
		beerModel.calculateAndUpdateAverageRating();

		final JSONObject newAverageRating = new JSONObject();
		newAverageRating.put(NodeProperty.Beer.AVERAGE_RATING, beerModel.getAverageRatingShortened());
		return new JsonRepresentation(newAverageRating.toString());
	}

}
