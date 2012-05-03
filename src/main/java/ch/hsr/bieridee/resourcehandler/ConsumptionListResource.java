package ch.hsr.bieridee.resourcehandler;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.graphdb.NotFoundException;
import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BeerModel;
import ch.hsr.bieridee.models.ConsumptionModel;
import ch.hsr.bieridee.models.UserModel;
import ch.hsr.bieridee.resourcehandler.interfaces.ICollectionResource;

/**
 * Server resource to provide access to users.
 */
public class ConsumptionListResource extends ServerResource implements ICollectionResource {

	private String username;
	private long beerId;

	@Override
	public void doInit() {
		this.username = (String) getRequestAttributes().get(Res.USER_REQ_ATTR);
		this.beerId = Long.parseLong((String) (getRequestAttributes().get(Res.BEER_REQ_ATTR)));
	}

	@Override
	public Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException {
		// TODO
		return new StringRepresentation("not implemented");

	}

	@Override
	public void store(Representation consumption) throws JSONException, IOException, NotFoundException, WrongNodeTypeException {
		final JSONObject ratingJSON = new JSONObject(consumption.getText());
		final BeerModel beerModel = new BeerModel(this.beerId);
		final UserModel userModel = new UserModel(this.username);

		ConsumptionModel.create(beerModel, userModel);
		System.out.println("consumption stored. Beer: " + beerModel.getName() + ", User: " + userModel.getUsername());
	}
}
