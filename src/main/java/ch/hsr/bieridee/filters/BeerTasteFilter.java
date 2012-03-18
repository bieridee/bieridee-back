package ch.hsr.bieridee.filters;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Status;
import org.restlet.routing.Filter;

public class BeerTasteFilter extends Filter {

	@Override
	protected int beforeHandle(Request request, Response response) {
		int status = CONTINUE;
		if (request.getAttributes().get("BeerName").equals("Heineken")) {
			response.setStatus(Status.CLIENT_ERROR_BAD_REQUEST,
					"Sorry bad taste");
			status = STOP;
		}

		return status;
	}
}
