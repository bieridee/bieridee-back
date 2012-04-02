package ch.hsr.bieridee;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import ch.hsr.bieridee.resourcehandler.BeerListResource;
import ch.hsr.bieridee.resourcehandler.BeerResource;

/**
 * Dispatcher for the RESTlet resources.
 * 
 * @author cfaessle, jfurrer
 *
 */
public class Dispatcher extends Application {

	/**
	 * Creates a root Restlet that will receive all incoming calls.
	 * 
	 * @return A restlet router
	 */
	@Override
	public synchronized Restlet createInboundRoot() {
		// Create a router Restlet that routes each call to a
		// new instance of HelloWorldResource.
		final Router router = new Router(getContext());

		// Defines only one route
		router.attach("/beers/{beer-id}", BeerResource.class);
		router.attach("/beers", BeerListResource.class);
		return router;
	}

}
