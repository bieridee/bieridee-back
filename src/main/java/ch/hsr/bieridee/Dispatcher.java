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
		
		// Add guarded resources
		rootRouter.attach(Res.BEER_COLLECTION, BeerListResource.class);
		rootRouter.attach(Res.BEER_DOCUMENT, BeerResource.class);
		rootRouter.attach(Res.RATING_DOCUMENT, RatingResource.class);
		rootRouter.attach(Res.CONSUMPTION_DOCUMENT, ConsumptionListResource.class);
		rootRouter.attach(Res.CONSUMPTION_BEER_COLLECTION, ConsumptionListResource.class);
		rootRouter.attach(Res.BREWERY_COLLECTION, BreweryListResource.class);
		rootRouter.attach(Res.BREWERY_DOCUMENT, BreweryResource.class);
		rootRouter.attach(Res.BEERTYPE_COLLECTION, BeertypeListResource.class);
		rootRouter.attach(Res.BEERTYPE_DOCUMENT, BeertypeResource.class);
		rootRouter.attach(Res.TAG_COLLECTION, TagListResource.class);
		rootRouter.attach(Res.TAG_DOCUMENT, TagResource.class);
		rootRouter.attach(Res.TAG_COLLECTION_BEER, TagListResourceForBeer.class);
		rootRouter.attach(Res.USER_COLLECTION, UserListResource.class);
		rootRouter.attach(Res.USERCREDENTIALS_CONTROLLER, UserCredentialsResource.class);
		rootRouter.attach(Res.IMAGE_DOCUMENT, ImageResource.class);
		rootRouter.attach(Res.TIMELINE_COLLECTION, TimelineResource.class);
		rootRouter.attach(Res.LOADTEST, Resource42.class);
		rootRouter.attach(Res.BRAND_COLLECTION, BrandListResource.class);
		rootRouter.attach(Res.BREWERYSIZE_COLLECTION, BrewerySizeResource.class);


		// Create a guard
		final ChallengeAuthenticator guard = new ChallengeAuthenticator(getContext(), BierideeHmacHelper.SCHEME, "BierIdee API");
		guard.setVerifier(new HmacSha256Verifier());
		guard.setNext(guardedRouter);
		rootRouter.attach(guard);

		return rootRouter;
	}

}
