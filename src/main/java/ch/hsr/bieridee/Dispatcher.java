package ch.hsr.bieridee;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.resourcehandler.BeerListResource;
import ch.hsr.bieridee.resourcehandler.BeerResource;
import ch.hsr.bieridee.resourcehandler.BeertypeListResource;
import ch.hsr.bieridee.resourcehandler.BeertypeResource;
import ch.hsr.bieridee.resourcehandler.TagListResource;
import ch.hsr.bieridee.resourcehandler.TagResource;
import ch.hsr.bieridee.resourcehandler.UserListResource;
import ch.hsr.bieridee.resourcehandler.UserResource;

/**
 * Dispatcher for the RESTlet resources.
 * 
 * @author cfaessle, jfurrer
 * 
 */
public class Dispatcher extends Application {

	/**
	 * Creates the dispatcher and thus the application.
	 */
	public Dispatcher() {
		setAuthor("dbrgen, cfaessle, jfurrer");
		setDescription("Social beer-app. REST API");
		setName("BierIdee");

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
		router.attach(Res.BEERTYPE_COLLECTION, BeertypeListResource.class);
		router.attach(Res.BEERTYPE_DOCUMENT, BeertypeResource.class);
		router.attach(Res.TAG_COLLECTION, TagListResource.class);
		router.attach(Res.TAG_DOCUMENT, TagResource.class);
		router.attach(Res.USER_COLLECTION, UserListResource.class);
		router.attach(Res.USER_DOCUMENT, UserResource.class);

		return router;
	}

}
