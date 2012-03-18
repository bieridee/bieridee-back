package ch.hsr.bieridee.resourceHandler;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.data.Status;

import ch.hsr.bieridee.domain.Beer;
import ch.hsr.bieridee.persistence.PersistenceFactory;

public class BierResource extends Restlet {
	public BierResource(Context context) {
		super(context);
	}

	@Override
	public void handle(Request request, Response response) {
		String requestedBeerName = (String) request.getAttributes().get("BierName");
		Beer beer = PersistenceFactory.getPersistenceProvider().getBierByName(
				requestedBeerName);

		String message = "Hey Fellar, You wanna Bier named " + requestedBeerName
				+ " here it is:\n" + beer;
		response.setEntity(message, MediaType.TEXT_PLAIN);
	}

}
