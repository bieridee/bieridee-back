package ch.hsr.bieridee.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.neo4j.graphdb.NotFoundException;

import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BeerModel;
import ch.hsr.bieridee.models.BeertypeModel;
import ch.hsr.bieridee.models.RatingModel;
import ch.hsr.bieridee.models.RecommendationModel;
import ch.hsr.bieridee.models.UserModel;

/**
 * This is the R3COMMENDAT0R.
 *                                             _                       
 *   THE                                      | |      _               
 *   ____ ____ ____ ___  ____   ____ ____   _ | | ____| |_  ___   ____ 
 *  / ___) _  ) ___) _ \|    \ / _  )  _ \ / || |/ _  |  _)/ _ \ / ___)
 * | |  ( (/ ( (__| |_| | | | ( (/ /| | | ( (_| ( ( | | |_| |_| | |     
 * |_|   \____)____)___/|_|_|_|\____)_| |_|\____|\_||_|\___)___/|_|  
 * 
 */
public class Recommendator {

	private UserModel userModel;
	private String userId;

	/**
	 * Creates a new Recomendator for the given User.
	 * 
	 * @param userModel
	 *            The user who (desperatly) desires recommendations
	 */
	public Recommendator(UserModel userModel) {
		this.userModel = userModel;
		this.userId = new Long(userModel.getId()).toString();
	}

	/**
	 * Initates the magics. Calculates the Recommendations.
	 * 
	 * @return The recommendations
	 * @throws WrongNodeTypeException
	 *             Thrown if a node has the wrong type
	 * @throws NotFoundException
	 *             Thrwons if a node was not found
	 */
	public List<RecommendationModel> calulateRecommendations() throws NotFoundException, WrongNodeTypeException {
		// ****
		// step one: weight the "like-neighbours"
		// ****
		// get a list of common ratings >= 3 (ratings for the same beer) of like-neighbours and self
		final List<RatingModel> ratingsOfLikeNeighbours = this.getCommonRatingsOfLikeNeighbours();
		// get a map with the weights for every user
		final Map<UserModel, Double> userWeights = this.calculateUserWeights(ratingsOfLikeNeighbours);

		// ****
		// step two: weight the beertypes
		// ****
		// get a list of own ratings >= 3
		final List<RatingModel> ownRatings = this.getRelevantRatingsOfUser();
		// extract the beertypes and weight them
		final Map<BeertypeModel, Double> beertypeWeights = this.calculateBeertypeWeights(ownRatings);

		// ****
		// step three: weight the beers, liked by like-neighbours
		// ****
		// get a list of ratings >= 3 of beers the like-neighbours like
		final List<RatingModel> beerRatings = this.getRatingsOfLikeNeighbours();
		// weight the rated beers by the rating, take the average weight if there is more than one rating
		final Map<BeerModel, Double> beerWeights = this.calculateBeerWeights(beerRatings);

		// ****
		// the grande finale...
		// ****
		// get a list of already known beers to filter them out
		final List<BeerModel> knownBeers = getKnownBeers();
		// throw all weights thogheter and get a list of recommedations from it
		final List<RecommendationModel> recommendations = this.calculate(beerRatings, userWeights, beertypeWeights, beerWeights, knownBeers);

		Collections.sort(recommendations);
		return recommendations;
	}

	// userweight step: start

	private List<RatingModel> getCommonRatingsOfLikeNeighbours() throws NotFoundException, WrongNodeTypeException {
		return RatingModel.createModelsFromNodes(Cypher.executeAndGetNodes(Cypherqueries.GET_LIKENEIGHBOURS_RATINGS_OF_COMMON_BEERS, "Friendaction", this.userId));
	}

	private Map<UserModel, Double> calculateUserWeights(List<RatingModel> likeNeighbourRatings) {
		final HashMap<UserModel, Double> userWeights = new HashMap<UserModel, Double>();
		// do nothing if list is empty
		if (likeNeighbourRatings.isEmpty()) {
			return userWeights;
		}
		// iterate over all ratings to weight and sum them up for every user
		for (RatingModel rating : likeNeighbourRatings) {
			Double value = null;
			final UserModel key = rating.getUser();
			value = userWeights.get(key);
			if (value == null) {
				value = applyRatingConversionTable(rating.getRating());
			} else {
				value += applyRatingConversionTable(rating.getRating());
			}
			userWeights.put(key, value);
		}
		// now normalize them in a scale from 1 to 3
		final Double max = Collections.max(userWeights.values());
		for (Entry<UserModel, Double> entry : userWeights.entrySet()) {
			final Double value = 1d + (2 * (entry.getValue() / max));
			entry.setValue(value);
		}
		return userWeights;
	}

	// / userweight step: end

	// beertypeweight step: start

	private List<RatingModel> getRelevantRatingsOfUser() throws NotFoundException, WrongNodeTypeException {
		return RatingModel.createModelsFromNodes(Cypher.executeAndGetNodes(Cypherqueries.GET_ACTIVE_RATINGS_FOR_USER, "Action", this.userId));
	}

	private Map<BeertypeModel, Double> calculateBeertypeWeights(final List<RatingModel> ownRatings) throws WrongNodeTypeException {
		final HashMap<BeertypeModel, Double> beertypeWeights = new HashMap<BeertypeModel, Double>();
		// return if list is empty
		if(ownRatings.isEmpty()) {
			return beertypeWeights;
		}
		// iterate over all own ratings to extract beertype to weight and sum them up
		for (RatingModel rating : ownRatings) {
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
		// normalize between 1 and 2
		final Double max = Collections.max(beertypeWeights.values());
		for (Entry<BeertypeModel, Double> entry : beertypeWeights.entrySet()) {
			final Double value = 1d + (entry.getValue() / max);
			entry.setValue(value);
		}
		return beertypeWeights;
	}

	// beertypeweight step: end

	// beerweight step: start

	private List<RatingModel> getRatingsOfLikeNeighbours() throws NotFoundException, WrongNodeTypeException {
		return RatingModel.createModelsFromNodes(Cypher.executeAndGetNodes(Cypherqueries.GET_ACTIVE_RATINGS_OF_LIKENEIGHBOURS, "OtherFriendAction", this.userId));
	}

	private Map<BeerModel, Double> calculateBeerWeights(List<RatingModel> beerRatings) {
		final HashMap<BeerModel, Double> beerWeights = new HashMap<BeerModel, Double>();
		final HashMap<BeerModel, LinkedList<Double>> tempBeerWeights = new HashMap<BeerModel, LinkedList<Double>>();
		// nothing to do if list is empty
		if(beerRatings.isEmpty()) {
			return beerWeights;
		}
		for (RatingModel rating : beerRatings) {
			// we need to take the average, thus we neet to keep track of the number of ratings
			LinkedList<Double> values = null;
			final BeerModel key = rating.getBeer();
			values = tempBeerWeights.get(key);
			if (values == null) {
				values = new LinkedList<Double>();
			}
			values.add(this.applyRatingConversionTable(rating.getRating()));
			tempBeerWeights.put(key, values);
		}
		// caluclate the average
		for (Entry<BeerModel, LinkedList<Double>> entry : tempBeerWeights.entrySet()) {
			final Double value = sum(entry.getValue()) / entry.getValue().size();
			beerWeights.put(entry.getKey(), value);
		}
		return beerWeights;
	}

	// beerweight step: end

	// finale step: start

	private List<BeerModel> getKnownBeers() throws NotFoundException, WrongNodeTypeException {
		return BeerModel.createModelsFromNodes(Cypher.executeAndGetNodes(Cypherqueries.GET_KNOWN_BEERS_OF_USER, "Beers", this.userId));
	}

	private List<RecommendationModel> calculate(List<RatingModel> beerRatings, Map<UserModel, Double> userWeights, Map<BeertypeModel, Double> beertypeWeights, Map<BeerModel, Double> beerWeights, List<BeerModel> knownBeers) throws NotFoundException,
			WrongNodeTypeException {
		final HashMap<BeerModel, Double> calcRecommendation = new HashMap<BeerModel, Double>();
		final LinkedList<RecommendationModel> recommendations = new LinkedList<RecommendationModel>();

		// iterate over all the beers rated by the like-neighbours, these are the recommended beers
		for (RatingModel rating : beerRatings) {
			final BeerModel beer = rating.getBeer();

			// no recommendation needed for already known beers, will not show up in list
			if (isBeerKnown(beer, knownBeers)) {
				continue;
			}

			final UserModel user = rating.getUser();
			final BeertypeModel beertype = beer.getBeertype();

			Double beerWeight = 1d;
			final Double calculatedBeerWeight = beerWeights.get(beer);
			if(calculatedBeerWeight != null) {
				beerWeight = calculatedBeerWeight;
			}
			
			Double userWeight = 1d;
			final Double claculatedUserWeight = userWeights.get(user);
			if(claculatedUserWeight != null) {
				userWeight = claculatedUserWeight;
			}
			// it is not given, that all beertypes of the recommended beers are rated, if not, use 1 as weight (no
			// influence)
			Double beertypeWeight = 1d;
			final Double calculatedBeertypeWeight = beertypeWeights.get(beertype);
			if (calculatedBeertypeWeight != null) {
				beertypeWeight = calculatedBeertypeWeight;
			}
			
			// this is the recommendation weight of the beer
			final Double recommendationWeight = userWeight * beerWeight * beertypeWeight;

			// summ it up
			Double value = null;
			value = calcRecommendation.get(beer);
			if (value == null) {
				value = recommendationWeight;
			} else {
				value += recommendationWeight;
			}
			calcRecommendation.put(beer, value);

		}
		// return an empty list if no recommendations could be made
		if(calcRecommendation.isEmpty()) {
			return recommendations;
		}
		// add a normalized weight
		final double max = Collections.max(calcRecommendation.values());
		for (Entry<BeerModel, Double> recom : calcRecommendation.entrySet()) {
			// SUPPRESS CHECKSTYLE: magic number -> normalize to max 5
			final double normalizedWeight = 1 + 4 * (recom.getValue() / max);
			recommendations.add(new RecommendationModel(recom.getValue(), normalizedWeight, this.userModel, recom.getKey()));
		}

		return recommendations;
	}

	// finale step: end

	/**
	 * Converts the ratings from 3 to 5 to according weights. 3 -> 0.5 4 -> 1 5 -> 2
	 * 
	 * @param value
	 *            The rating to be converted
	 * @return The weight of the rating
	 */
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

	/**
	 * Sums up the numbers in the given list.
	 * 
	 * @param numbers
	 *            Nubers to be summed up
	 * @return The sum
	 */
	private double sum(LinkedList<Double> numbers) {
		double retSum = 0;
		for (Double i : numbers) {
			retSum += i;
		}
		return retSum;
	}

	private boolean isBeerKnown(BeerModel beer, List<BeerModel> knownBeers) {
		return knownBeers.contains(beer);
	}

}
