package ch.hsr.bieridee.resourcehandler;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.graphdb.NotFoundException;
import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BeerModel;
import ch.hsr.bieridee.models.BeertypeModel;
import ch.hsr.bieridee.models.BreweryModel;
import ch.hsr.bieridee.resourcehandler.interfaces.ICollectionResource;

/**
 * ServerResource for getting a List of Beers.
 */
public class BeerListResource extends ServerResource implements ICollectionResource {

	private static final int ITEM_COUNT_DEFAULT = 12;

	@Override
	public Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException {

		List<BeerModel> beerModels;
		final boolean needsPaging = getNeedsPaging();
		final int items = getNumberOfItemsParam();
		final int page = getPageNumberParam();
		final String tagId = getQuery().getFirstValue(Res.BEER_FILTER_PARAMETER_TAG);

		if (tagId != null) {
			if (needsPaging) {
				beerModels = BeerModel.getAll(Long.parseLong(tagId), items, page * items);
			} else {
				beerModels = BeerModel.getAll(Long.parseLong(tagId));
			}
		} else {
			if (needsPaging) {
				beerModels = BeerModel.getAll(items, page * items);
			} else {
				beerModels = BeerModel.getAll();
			}
		}

		final BeerModel[] beerModelArray = beerModels.toArray(new BeerModel[beerModels.size()]);

		// json representation
		final JacksonRepresentation<BeerModel[]> beerArrayJacksonRep = new JacksonRepresentation<BeerModel[]>(beerModelArray);
		beerArrayJacksonRep.setObjectMapper(Config.getObjectMapper());

		return beerArrayJacksonRep;
	}

	@Override
	public Representation store(Representation beer) throws JSONException, IOException, NotFoundException, WrongNodeTypeException {
		final JSONObject beerJson = new JSONObject(beer.getText());

		final String name = beerJson.getString("name");
		final String brand = beerJson.getString("brand");

		BeertypeModel beertypeModel = null;
		// is the beertype unknown?
		if (beerJson.optBoolean("unknownbeertype")) {
			beertypeModel = BeertypeModel.getUnknown();
		} else {
			beertypeModel = new BeertypeModel(beerJson.getLong("beertype"));
		}

		BreweryModel breweryModel = null;
		// ist the brewery unknown?
		if (beerJson.optBoolean("unknownbrewery")) {
			breweryModel = BreweryModel.getUnknown();
		} else {
			breweryModel = new BreweryModel(beerJson.getLong("brewery"));
		}
		final BeerModel newBeerModel = BeerModel.create(name, brand, beertypeModel, breweryModel);

		final JacksonRepresentation<BeerModel> newBeerRep = new JacksonRepresentation<BeerModel>(newBeerModel);
		newBeerRep.setObjectMapper(Config.getObjectMapper());

		return newBeerRep;
	}

	private boolean getNeedsPaging() {
		if (getQuery().getFirstValue(Res.PAGE_PARAMETER) == null && getQuery().getFirstValue(Res.ITEMS_PER_PAGE_PARAMETER) == null) {
			return false;
		}
		return true;
	}

	private int getPageNumberParam() {
		final String pageNumber = getQuery().getFirstValue(Res.PAGE_PARAMETER);
		if (pageNumber != null) {
			return Integer.parseInt(pageNumber);
		}
		return 0;
	}

	private int getNumberOfItemsParam() {
		final String nOfItemsParam = getQuery().getFirstValue(Res.ITEMS_PER_PAGE_PARAMETER);
		if (nOfItemsParam != null) {
			return Integer.parseInt(nOfItemsParam);
		}
		return ITEM_COUNT_DEFAULT;
	}

}
