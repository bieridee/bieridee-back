package ch.hsr.bieridee.resourcehandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.neo4j.graphdb.NotFoundException;
import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BeertypeModel;
import ch.hsr.bieridee.models.RatingModel;
import ch.hsr.bieridee.models.UserModel;
import ch.hsr.bieridee.resourcehandler.interfaces.IReadOnlyResource;
import ch.hsr.bieridee.utils.Cypher;
import ch.hsr.bieridee.utils.Cypherqueries;
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
		final UserModel userModel = new UserModel(this.username);
		final List<RatingModel> ratingsGreater3 = this.getUserRatings(userModel);
		final List<RatingModel> friendRatings = getFriendRatings(userModel);

		final Map<BeertypeModel, Double> beertypeWeights = createBeertypeWeightMap(ratingsGreater3);
		final Map<UserModel, Double> userWeights = createUserWeights(friendRatings);
		System.out.println("BeertypeWeights: " + beertypeWeights);
		System.out.println("FriendRatings: " + friendRatings);
		// json representation

		return null;
	}

	private Map<UserModel, Double> createUserWeights(List<RatingModel> friendRatings) {
		final HashMap<UserModel, Double> userRatings = new HashMap<UserModel, Double>();
		for (RatingModel rating : friendRatings) {
			Double value = null;
			value = userRatings.get(rating.getUser());
			if (value == null) {
				value = calculateRatingWeight(rating.getRating());
			} else {
				value += calculateRatingWeight(rating.getRating());
			}
			userRatings.put(rating.getUser(), value);
		}
		final Double max = Collections.max(userRatings.values());
		for (Entry<UserModel, Double> entry : userRatings.entrySet()) {
			final Double value = 1d + (entry.getValue() / max);
			entry.setValue(value);
		}
		return userRatings;
	}

	private List<RatingModel> getFriendRatings(UserModel user) throws NotFoundException, WrongNodeTypeException {
		return RatingModel.createModelsFromNodes(Cypher.executeAndGetNodes(Cypherqueries.GET_FRIEND_RATINGS, "Friendaction", user.getId() + ""));
	}

	private Map<BeertypeModel, Double> createBeertypeWeightMap(final List<RatingModel> ratingsOfUserGreater3) throws WrongNodeTypeException {
		final HashMap<BeertypeModel, Double> beertypeWeights = new HashMap<BeertypeModel, Double>();
		for (RatingModel rating : ratingsOfUserGreater3) {
			Double value = null;
			value = beertypeWeights.get(rating.getBeer().getBeertype());
			if (value == null) {
				value = calculateRatingWeight(rating.getRating());
			} else {
				value += calculateRatingWeight(rating.getRating());
			}
			beertypeWeights.put(rating.getBeer().getBeertype(), value);
		}
		final Double max = Collections.max(beertypeWeights.values());
		for (Entry<BeertypeModel, Double> entry : beertypeWeights.entrySet()) {
			final Double value = 1d + (entry.getValue() / max);
			entry.setValue(value);
		}
		return beertypeWeights;
	}

	public List<RatingModel> getUserRatings(UserModel user) throws NotFoundException, WrongNodeTypeException {
		return RatingModel.createModelsFromNodes(Cypher.executeAndGetNodes(Cypherqueries.GET_ACTIVE_RATINGS_FOR_USER_GREATER_3, "Action", user.getId() + ""));
	}

	private double calculateRatingWeight(int value) {
		switch (value) {
			case 3:
				return 0.5;
			case 4:
				return 1;
			case 5:
				return 2;
		}
		return 0;
	}
}
