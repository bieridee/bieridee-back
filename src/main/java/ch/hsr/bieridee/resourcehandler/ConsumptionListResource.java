package ch.hsr.bieridee.resourcehandler;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.neo4j.graphdb.NotFoundException;
import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Config;
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
		List<ConsumptionModel> consumptionModels = new LinkedList<ConsumptionModel>();
		
		if(this.username != null) { // get consumptions for user/beer
			consumptionModels = ConsumptionModel.getAll(this.beerId, this.username);
		} else { // get all consumptions for the beer, no user given
			consumptionModels = ConsumptionModel.getAll(this.beerId);
		}
		
		final ConsumptionModel[] consumptionModelArray = consumptionModels.toArray(new ConsumptionModel[consumptionModels.size()]);
		final JacksonRepresentation<ConsumptionModel[]> consumptionsRep = new JacksonRepresentation<ConsumptionModel[]>(consumptionModelArray);
		consumptionsRep.setObjectMapper(Config.getObjectMapper());
		
		return consumptionsRep;
	}

	@Override
	public void store(Representation consumption) throws JSONException, IOException, NotFoundException, WrongNodeTypeException {
		final BeerModel beerModel = new BeerModel(this.beerId);
		final UserModel userModel = new UserModel(this.username);

		ConsumptionModel.create(beerModel, userModel);
	}
}
