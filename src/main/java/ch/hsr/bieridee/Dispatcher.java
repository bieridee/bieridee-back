package ch.hsr.bieridee;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.resourcehandler.BeerListResource;
import ch.hsr.bieridee.resourcehandler.BeerResource;
import ch.hsr.bieridee.resourcehandler.BeertypeListResource;
import ch.hsr.bieridee.resourcehandler.BeertypeResource;
import ch.hsr.bieridee.resourcehandler.BreweryListResource;
import ch.hsr.bieridee.resourcehandler.BreweryResource;
import ch.hsr.bieridee.resourcehandler.ConsumptionListResource;
import ch.hsr.bieridee.resourcehandler.ImageResource;
import ch.hsr.bieridee.resourcehandler.RatingResource;
import ch.hsr.bieridee.resourcehandler.RecommendationResource;
import ch.hsr.bieridee.resourcehandler.Resource42;
import ch.hsr.bieridee.resourcehandler.TagListResource;
import ch.hsr.bieridee.resourcehandler.TagResource;
import ch.hsr.bieridee.resourcehandler.TimelineResource;
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
		router.attach(Res.USER_DOCUMENT, UserResource.class);
		router.attach(Res.IMAGE_DOCUMENT, ImageResource.class);
		router.attach(Res.TIMELINE_COLLECTION, TimelineResource.class);
		router.attach(Res.USER_RECOMMENDATION_COLLECTION, RecommendationResource.class);
		router.attach(Res.LOADTEST, Resource42.class);

		return router;
	}

}
