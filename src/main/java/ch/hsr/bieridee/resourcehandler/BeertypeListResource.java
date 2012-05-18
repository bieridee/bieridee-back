package ch.hsr.bieridee.resourcehandler;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.data.Status;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BeertypeModel;
import ch.hsr.bieridee.resourcehandler.interfaces.ICollectionResource;

/**
 * Beertype list resource.
 * 
 */
public class BeertypeListResource extends ServerResource implements ICollectionResource {

	@Override
	public Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException {

		final List<BeertypeModel> beertypeModelList = BeertypeModel.getAll();

		final BeertypeModel[] beertypeModelArray = beertypeModelList.toArray(new BeertypeModel[beertypeModelList.size()]);

		final JacksonRepresentation<BeertypeModel[]> beertypesJacksonRep = new JacksonRepresentation<BeertypeModel[]>(beertypeModelArray);
		beertypesJacksonRep.setObjectMapper(Config.getObjectMapper());

		return beertypesJacksonRep;
	}

	@Override
	public Representation store(Representation rep) throws JSONException, IOException {
		final JSONObject beertypeJSON = new JSONObject(rep.getText());
		final String name = beertypeJSON.getString("name");
		final String description = beertypeJSON.getString("description");
		final BeertypeModel beertypeModel = BeertypeModel.create(name, description);

		final JacksonRepresentation<BeertypeModel> newBeertypeRep = new JacksonRepresentation<BeertypeModel>(beertypeModel);
		newBeertypeRep.setObjectMapper(Config.getObjectMapper());

		setStatus(Status.SUCCESS_CREATED);
		return newBeertypeRep;
	}

}
