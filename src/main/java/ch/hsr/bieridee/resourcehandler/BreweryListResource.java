package ch.hsr.bieridee.resourcehandler;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;

import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BreweryModel;
import ch.hsr.bieridee.resourcehandler.interfaces.ICollectionResource;

/**
 * ServerResource for getting a List of Breweries.
 */
public class BreweryListResource extends AbstractPagingServerResource implements ICollectionResource {

	@Override
	public Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException {
		List<BreweryModel> breweryModels;
		final boolean needsPaging = getNeedsPaging();
		final int items = getNumberOfItemsParam();
		final int page = getPageNumberParam();
		final String brewerySize = getQuery().getFirstValue(Res.BREWERY_FILTER_PARAMETER_SIZE);

		if (brewerySize != null) {
			if (needsPaging) {
				breweryModels = BreweryModel.getAll(brewerySize, items, items * page);
			} else {
				breweryModels = BreweryModel.getAll(brewerySize);
			}
		} else {
			if (needsPaging) {
				breweryModels = BreweryModel.getAll(items, items * page);
			} else {
				breweryModels = BreweryModel.getAll();
			}
		}

		final BreweryModel[] breweryModelArray = breweryModels.toArray(new BreweryModel[breweryModels.size()]);

		// Jackson representation
		final JacksonRepresentation<BreweryModel[]> breweryArrayJacksonRep = new JacksonRepresentation<BreweryModel[]>(breweryModelArray);
		breweryArrayJacksonRep.setObjectMapper(Config.getObjectMapper());

		return breweryArrayJacksonRep;
	}

	@Override
	public Representation store(Representation rep) throws JSONException, IOException {
		final JSONObject breweryJSON = new JSONObject(rep.getText());
		final String name = breweryJSON.getString("name");
		final String description = breweryJSON.getString("description");
		final String size = breweryJSON.getString("size");
		final String picture = breweryJSON.getString("picture");
		final BreweryModel breweryModel = BreweryModel.create(name, description, picture, size);

		final JacksonRepresentation<BreweryModel> newBreweryRep = new JacksonRepresentation<BreweryModel>(breweryModel);
		newBreweryRep.setObjectMapper(Config.getObjectMapper());

		return newBreweryRep;
	}

}
