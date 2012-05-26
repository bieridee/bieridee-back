package ch.hsr.bieridee.resourcehandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.neo4j.graphdb.NotFoundException;
import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BeerModel;
import ch.hsr.bieridee.models.BeertypeModel;
import ch.hsr.bieridee.models.RatingModel;
import ch.hsr.bieridee.models.RecommendationModel;
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
		
		final List<RatingModel> ratingsGreater3 = this.getRelevantRatingsOfUser(userModel);
		final List<RatingModel> friendRatings = this.getRelevantRatingsOfLikeNeighbours(userModel);
		final List<RatingModel> beerRatings = this.getRatingsOfFriends(userModel);
		final List<BeerModel> knownBeers = this.getKnownBeers(userModel);

		final Map<BeertypeModel, Double> beertypeWeights = createBeertypeWeightMap(ratingsGreater3);
		
		final Map<UserModel, Double> userWeights = createUserWeights(friendRatings);
		
		final Map<BeerModel, Double> beerWeights = createBeerWeights(beerRatings);
		
		final List<RecommendationModel> recommendations = calculateRecommendations(beerRatings, userWeights, beertypeWeights, beerWeights, knownBeers);
		
		final JacksonRepresentation<RecommendationModel[]> recomRep = new JacksonRepresentation<RecommendationModel[]>(recommendations.toArray(new RecommendationModel[recommendations.size()]));
		recomRep.setObjectMapper(Config.getObjectMapper());

		return recomRep;
	}

	private List<BeerModel> getKnownBeers(UserModel user) throws NotFoundException, WrongNodeTypeException {
		return BeerModel.createModelsFromNodes(Cypher.executeAndGetNodes(Cypherqueries.GET_KNOWN_BEERS_OF_USER, "Beers", user.getId() + ""));
	}

	private List<RecommendationModel> calculateRecommendations(List<RatingModel> beerRatings, Map<UserModel, Double> userWeights, Map<BeertypeModel, Double> beertypeWeights, Map<BeerModel, Double> beerWeights, List<BeerModel> knownBeers) throws NotFoundException, WrongNodeTypeException {
		final HashMap<BeerModel, Double> calcRecommendation = new HashMap<BeerModel, Double>();
		final LinkedList<RecommendationModel> recommendations = new LinkedList<RecommendationModel>();
		
		for (RatingModel rating : beerRatings) {
			final BeerModel beer = rating.getBeer();
			
			// no rating needed for already known beers
			if(isBeerKnown(beer, knownBeers)) {
				continue;
			}
			
			final UserModel user = rating.getUser();
			final BeertypeModel beertype = beer.getBeertype();
			
			final Double beerWeight = beerWeights.get(beer);
			final Double userWeight = userWeights.get(user);
			Double beertypeWeight = 1d;
			if(beertypeWeights.get(beertype) != null) {
				beertypeWeight = beertypeWeights.get(beertype);
			}
			
			final Double recommendationWeight = userWeight * beerWeight * beertypeWeight;
			
			Double value = null;
			value = calcRecommendation.get(beer);
			if (value == null) {
				value = recommendationWeight;
			} else {
				value += recommendationWeight;
			}
			calcRecommendation.put(beer, value);
			
		}
		
		final UserModel userModel = new UserModel(this.username);
		final double max = Collections.max(calcRecommendation.values());
		for(Entry<BeerModel, Double> recom : calcRecommendation.entrySet()) {
			//SUPPRESS CHECKSTYLE: magic number -> normalize to max 5
			final double normalizedWeight = 1 + 4 * (recom.getValue() / max);
			recommendations.add(new RecommendationModel(recom.getValue(), normalizedWeight, userModel, recom.getKey()));
		}
		
		return recommendations;
	}

	private boolean isBeerKnown(BeerModel beer, List<BeerModel> knownBeers) {
		return knownBeers.contains(beer);
	}

	private Map<BeerModel, Double> createBeerWeights(List<RatingModel> beerRatings) {
		final HashMap<BeerModel, Double> beerWeights = new HashMap<BeerModel, Double>();
		final HashMap<BeerModel, LinkedList<Double>> tempBeerWeights = new HashMap<BeerModel, LinkedList<Double>>();
		for (RatingModel rating : beerRatings) {
			// we need to take the average, thus we neet to keep track of the number of ratings
			LinkedList<Double> values = null;
			final BeerModel key = rating.getBeer();
			values = tempBeerWeights.get(key);
			if(values == null) {
				values = new LinkedList<Double>();
			}
			values.add(this.applyRatingConversionTable(rating.getRating()));
			tempBeerWeights.put(key, values);
		}
		// caluclate the average
		for(Entry<BeerModel, LinkedList<Double>> entry : tempBeerWeights.entrySet()) {
			final Double value = sum(entry.getValue()) / entry.getValue().size();
			beerWeights.put(entry.getKey(), value);
		}
		return beerWeights;
	}

	private List<RatingModel> getRatingsOfFriends(UserModel user) throws NotFoundException, WrongNodeTypeException {
		return RatingModel.createModelsFromNodes(Cypher.executeAndGetNodes(Cypherqueries.GET_ACTIVE_RATINGS_OF_FRIENDS, "OtherFriendAction", user.getId() + ""));
	}

	private Map<UserModel, Double> createUserWeights(List<RatingModel> friendRatings) {
		final HashMap<UserModel, Double> userRatings = new HashMap<UserModel, Double>();
		for (RatingModel rating : friendRatings) {
			Double value = null;
			final UserModel key = rating.getUser();
			value = userRatings.get(key);
			if (value == null) {
				value = applyRatingConversionTable(rating.getRating());
			} else {
				value += applyRatingConversionTable(rating.getRating());
			}
			userRatings.put(key, value);
		}
		final Double max = Collections.max(userRatings.values());
		for (Entry<UserModel, Double> entry : userRatings.entrySet()) {
			final Double value = 1d + (2 * (entry.getValue() / max));
			entry.setValue(value);
		}
		return userRatings;
	}

	private List<RatingModel> getRelevantRatingsOfLikeNeighbours(UserModel user) throws NotFoundException, WrongNodeTypeException {
		return RatingModel.createModelsFromNodes(Cypher.executeAndGetNodes(Cypherqueries.GET_FRIEND_RATINGS_OF_COMMON_BEERS, "Friendaction", user.getId() + ""));
	}

	private Map<BeertypeModel, Double> createBeertypeWeightMap(final List<RatingModel> ratingsOfUserGreater3) throws WrongNodeTypeException {
		final HashMap<BeertypeModel, Double> beertypeWeights = new HashMap<BeertypeModel, Double>();
		for (RatingModel rating : ratingsOfUserGreater3) {
			Double value = null;
			final BeertypeModel key = rating.getBeer().getBeertype();
			value = beertypeWeights.get(key);
			if (value == null) {
				value = applyRatingConversionTable(rating.getRating());
			} else {
				value += applyRatingConversionTable(rating.getRating());
			}
			beertypeWeights.put(key, value);
		}
		final Double max = Collections.max(beertypeWeights.values());
		for (Entry<BeertypeModel, Double> entry : beertypeWeights.entrySet()) {
			final Double value = 1d + (entry.getValue() / max);
			entry.setValue(value);
		}
		return beertypeWeights;
	}

	private List<RatingModel> getRelevantRatingsOfUser(UserModel user) throws NotFoundException, WrongNodeTypeException {
		return RatingModel.createModelsFromNodes(Cypher.executeAndGetNodes(Cypherqueries.GET_ACTIVE_RATINGS_FOR_USER_GREATER_3, "Action", user.getId() + ""));
	}

	private double applyRatingConversionTable(int value) {
		final int rating3 = 3;
		final int rating4 = 4;
		final int rating5 = 5;
		final double rating3conversion = 0.5;
		final double rating4conversion = 1;
		final double rating5conversion = 2;
		
		switch (value) {
			case rating3:
				return rating3conversion;
			case rating4:
				return rating4conversion;
			case rating5:
				return rating5conversion;
		}
		return 0;
	}
	
	private double sum(LinkedList<Double> numbers) {
	    double retSum = 0;
	    for(Double i : numbers) {
	        retSum += i;
	    }
	    return retSum;
	}
}
