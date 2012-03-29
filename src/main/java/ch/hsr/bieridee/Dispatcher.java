package ch.hsr.bieridee;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import ch.hsr.bieridee.resourcehandler.BierListResource;
import ch.hsr.bieridee.resourcehandler.BierServerResource;
import ch.hsr.bieridee.resourcehandler.Neo4jTestResource;

public class Dispatcher extends Application {

	/**
	 * Creates a root Restlet that will receive all incoming calls.
	 */
	@Override
	public synchronized Restlet createInboundRoot() {
		// Create a router Restlet that routes each call to a
		// new instance of HelloWorldResource.
		Router router = new Router(getContext());

		// Defines only one route
		router.attach("/neo4j", Neo4jTestResource.class);
		router.attach("/bier/{BierID}", BierServerResource.class);
		router.attach("/bier/", BierListResource.class);

		return router;
	}

}
