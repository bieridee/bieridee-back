package ch.hsr.bieridee.resourcehandler;

import java.io.IOException;

import org.apache.commons.lang.NotImplementedException;
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
import ch.hsr.bieridee.resourcehandler.interfaces.IDocumentResource;

/**
 * Beer resource.
 */
public class BeerResource extends ServerResource implements IDocumentResource {

	private long beerId;

	@Override
	public void doInit() {
		this.beerId = Long.parseLong((String) (getRequestAttributes().get(Res.BEER_REQ_ATTR)));
	}

	@Override
	public Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException {

		final BeerModel beerModel = new BeerModel(this.beerId);

		// json representation
		final JacksonRepresentation<BeerModel> beerJacksonRep = new JacksonRepresentation<BeerModel>(beerModel);
		beerJacksonRep.setObjectMapper(Config.getObjectMapper());

		return beerJacksonRep;
	}

	@Override
	public Representation store(Representation rep) throws JSONException, IOException, NotFoundException, WrongNodeTypeException {
		final JSONObject beerJson = new JSONObject(rep.getText());

		final String brand = beerJson.getString("brand");
		final String name = beerJson.getString("name");
		final long beertypeId = beerJson.getLong("beertype");
		final long breweryId = beerJson.getLong("brewery");

		final BeerModel bm = new BeerModel(this.beerId);
		final BreweryModel breweryModel = new BreweryModel(breweryId);
		final BeertypeModel beertypeModel = new BeertypeModel(beertypeId);

		bm.setName(name);
		bm.setBrand(brand);
		bm.setBeertype(beertypeModel);
		bm.setBrewery(breweryModel);

		final JacksonRepresentation<BeerModel> representation = new JacksonRepresentation<BeerModel>(bm);
		representation.setObjectMapper(Config.getObjectMapper());
		return representation;

	}

	@Override
	public void remove() throws NotFoundException, WrongNodeTypeException {
		throw new NotImplementedException(); // TODO
	}

}
