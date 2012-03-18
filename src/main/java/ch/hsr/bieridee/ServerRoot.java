package ch.hsr.bieridee;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.routing.Filter;
import org.restlet.routing.Router;

import ch.hsr.bieridee.filters.BeerTasteFilter;
import ch.hsr.bieridee.resourceHandler.BierResource;

public class ServerRoot extends Application {
	public static void main(String[] args) throws Exception {
		Server server = new Server(Protocol.HTTP, 8000, ServerRoot.class);
		server.setNext(new ServerRoot());
		server.start();

		/*
		 * Try Access with http://localhost:8000/Beer/Erdinger or to test the
		 * Filter with http://localhost:8000/Beer/Heineken
		 */
	}

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		BierResource beerResource = new BierResource(getContext());
		Filter beerTasteFilter = new BeerTasteFilter();
		beerTasteFilter.setNext(beerResource);
		router.attach("http://localhost:8000/Beer/{BeerName}", beerTasteFilter);

		return router;
	}

}
