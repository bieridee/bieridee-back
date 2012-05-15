package ch.hsr.bieridee;

import ch.hsr.bieridee.auth.BierideeHmacHelper;
import ch.hsr.bieridee.auth.HmacSha256Verifier;
import ch.hsr.bieridee.resourcehandler.*;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.services.BeerAppStatusService;
import org.restlet.security.ChallengeAuthenticator;

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

		// Create a router
		final Router router = new Router(getContext());
		router.attach(Res.BEER_COLLECTION, BeerListResource.class);
		router.attach(Res.BEER_DOCUMENT, BeerResource.class);
		router.attach(Res.RATING_DOCUMENT, RatingResource.class);
		router.attach(Res.CONSUMPTION_DOCUMENT, ConsumptionListResource.class);
		router.attach(Res.CONSUMPTION_BEER_COLLECTION, ConsumptionListResource.class);
		router.attach(Res.BREWERY_COLLECTION, BreweryListResource.class);
		router.attach(Res.BREWERY_DOCUMENT, BreweryResource.class);
		router.attach(Res.BEERTYPE_COLLECTION, BeertypeListResource.class);
		router.attach(Res.BEERTYPE_DOCUMENT, BeertypeResource.class);
		router.attach(Res.TAG_COLLECTION, TagListResource.class);
		router.attach(Res.TAG_DOCUMENT, TagResource.class);
		router.attach(Res.USER_COLLECTION, UserListResource.class);
		router.attach(Res.IMAGE_DOCUMENT, ImageResource.class);
		router.attach(Res.TIMELINE_COLLECTION, TimelineResource.class);
		router.attach(Res.LOADTEST, Resource42.class);

		router.attach(Res.USER_DOCUMENT, UserResource.class);
		router.attach(Res.USERCREDENTIALS_CONTROLLER, UserCredentialsResource.class);

		// Create a guard
		ChallengeAuthenticator guard = new ChallengeAuthenticator(
				getContext(), BierideeHmacHelper.SCHEME, "BierIdee API");
		guard.setVerifier(new HmacSha256Verifier());
		guard.setNext(router);

		return guard;
	}

}
