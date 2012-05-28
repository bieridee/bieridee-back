package ch.hsr.bieridee;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;
import org.restlet.security.ChallengeAuthenticator;

import ch.hsr.bieridee.auth.BierideeHmacHelper;
import ch.hsr.bieridee.auth.HmacSha256Verifier;
import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.resourcehandler.BeerListResource;
import ch.hsr.bieridee.resourcehandler.BeerResource;
import ch.hsr.bieridee.resourcehandler.BeertypeListResource;
import ch.hsr.bieridee.resourcehandler.BeertypeResource;
import ch.hsr.bieridee.resourcehandler.BrandListResource;
import ch.hsr.bieridee.resourcehandler.BreweryListResource;
import ch.hsr.bieridee.resourcehandler.BreweryResource;
import ch.hsr.bieridee.resourcehandler.BrewerySizeResource;
import ch.hsr.bieridee.resourcehandler.ConsumptionListResource;
import ch.hsr.bieridee.resourcehandler.ImageResource;
import ch.hsr.bieridee.resourcehandler.RatingResource;
import ch.hsr.bieridee.resourcehandler.RecommendationResource;
import ch.hsr.bieridee.resourcehandler.Resource42;
import ch.hsr.bieridee.resourcehandler.TagListResource;
import ch.hsr.bieridee.resourcehandler.TagListResourceForBeer;
import ch.hsr.bieridee.resourcehandler.TagResource;
import ch.hsr.bieridee.resourcehandler.TimelineResource;
import ch.hsr.bieridee.resourcehandler.UserCredentialsResource;
import ch.hsr.bieridee.resourcehandler.UserListResource;
import ch.hsr.bieridee.resourcehandler.UserResource;
import ch.hsr.bieridee.services.BeerAppStatusService;

/**
 * Dispatcher for the RESTlet resources.
 */
public class Dispatcher extends Application {

	/**
	 * Creates the dispatcher and thus the application.
	 */
	public Dispatcher() {
		this.setAuthor("dbargen, cfaessle, jfurrer");
		this.setDescription("Social beer-app. REST API");
		this.setName("BierIdee");
		this.setStatusService(new BeerAppStatusService());
	}

	/**
	 * Creates a root Restlet that will receive all incoming calls.
	 * 
	 * @return A restlet router
	 */
	@Override
	public synchronized Restlet createInboundRoot() {

		// Create routers
		final Router rootRouter = new Router(getContext());
		final Router guardedRouter = new Router(getContext());

		// Attach public resources
		rootRouter.attach(Res.USER_DOCUMENT, UserResource.class);
		rootRouter.attach(Res.TAG_COLLECTION, TagListResource.class);
		
		// Add guarded resources
		guardedRouter.attach(Res.BEER_COLLECTION, BeerListResource.class);
		guardedRouter.attach(Res.BEER_DOCUMENT, BeerResource.class);
		guardedRouter.attach(Res.RATING_DOCUMENT, RatingResource.class);
		guardedRouter.attach(Res.CONSUMPTION_DOCUMENT, ConsumptionListResource.class);
		guardedRouter.attach(Res.CONSUMPTION_BEER_COLLECTION, ConsumptionListResource.class);
		guardedRouter.attach(Res.BREWERY_COLLECTION, BreweryListResource.class);
		guardedRouter.attach(Res.BREWERY_DOCUMENT, BreweryResource.class);
		guardedRouter.attach(Res.BEERTYPE_COLLECTION, BeertypeListResource.class);
		guardedRouter.attach(Res.BEERTYPE_DOCUMENT, BeertypeResource.class);
		guardedRouter.attach(Res.TAG_DOCUMENT, TagResource.class);
		guardedRouter.attach(Res.TAG_COLLECTION_BEER, TagListResourceForBeer.class);
		guardedRouter.attach(Res.USER_COLLECTION, UserListResource.class);
		guardedRouter.attach(Res.USERCREDENTIALS_CONTROLLER, UserCredentialsResource.class);
		guardedRouter.attach(Res.IMAGE_DOCUMENT, ImageResource.class);
		guardedRouter.attach(Res.TIMELINE_COLLECTION, TimelineResource.class);
		guardedRouter.attach(Res.LOADTEST, Resource42.class);
		guardedRouter.attach(Res.BRAND_COLLECTION, BrandListResource.class);
		guardedRouter.attach(Res.BREWERYSIZE_COLLECTION, BrewerySizeResource.class);
		guardedRouter.attach(Res.USER_RECOMMENDATION_COLLECTION, RecommendationResource.class);


		// Create a guard
		final ChallengeAuthenticator guard = new ChallengeAuthenticator(getContext(), BierideeHmacHelper.SCHEME, "BierIdee API");
		guard.setVerifier(new HmacSha256Verifier());
		guard.setNext(guardedRouter);
		rootRouter.attach(guard);

		return rootRouter;
	}

}
