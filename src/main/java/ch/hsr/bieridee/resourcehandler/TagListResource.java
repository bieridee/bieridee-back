package ch.hsr.bieridee.resourcehandler;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.graphdb.NotFoundException;
import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.data.Status;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.auth.HmacSha256Verifier;
import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.exceptions.InvalidRequestException;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BeerModel;
import ch.hsr.bieridee.models.TagModel;
import ch.hsr.bieridee.resourcehandler.interfaces.ICollectionResource;

/**
 * Tag list resource.
 * 
 */
public class TagListResource extends ServerResource implements ICollectionResource {
	private long beerId;
	private int authStatus;
	private static final int NOT_EXISTING = -1;

	@Override
	public void doInit() {
		this.authStatus = new HmacSha256Verifier().verify(getRequest(), getResponse());
		final String param = getQuery().getFirstValue(Res.TAG_FILTER_PARAMETER_BEER);
		if (param != null) {
			this.beerId = Long.parseLong(param);
		} else {
			this.beerId = NOT_EXISTING;
		}
	}

	@Override
	public Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException {
		List<TagModel> tagModels = null;
		if (this.beerId > NOT_EXISTING) {
			final BeerModel bm = new BeerModel(this.beerId);
			tagModels = bm.getTagModels();
		} else {
			tagModels = TagModel.getAll();
		}

		final TagModel[] tagModelArray = tagModels.toArray(new TagModel[tagModels.size()]);
		final JacksonRepresentation<TagModel[]> tagsJacksonRep = new JacksonRepresentation<TagModel[]>(tagModelArray);
		tagsJacksonRep.setObjectMapper(Config.getObjectMapper());

		return tagsJacksonRep;
	}

	@Override
	public Representation store(Representation rep) throws JSONException, IOException, NotFoundException, WrongNodeTypeException, InvalidRequestException {
		// If auth header is present and valid, update user
		if (this.authStatus == HmacSha256Verifier.RESULT_VALID) {
			if (this.beerId == NOT_EXISTING) {
				throw new InvalidRequestException("Invalid Beerid");
			}
			final BeerModel bm = new BeerModel(this.beerId);

			final JSONObject tagJSON = new JSONObject(rep.getText());
			final String value = tagJSON.getString("value");
			TagModel tagModel;
			try {
				tagModel = TagModel.getByName(value);
			} catch (NotFoundException e) {
				tagModel = TagModel.create(value);
			}

			bm.addTag(tagModel);
			final JacksonRepresentation<TagModel> tagJacksonRep = new JacksonRepresentation<TagModel>(tagModel);
			tagJacksonRep.setObjectMapper(Config.getObjectMapper());
			this.setStatus(Status.SUCCESS_CREATED);
			return tagJacksonRep;

			// If auth header is and invalid or missing, return error
		} else {
			final String message = "Invalid or missing credentials.";
			this.respondWith(Status.CLIENT_ERROR_UNAUTHORIZED, message);
			return null;
		}
	}

	/**
	 * Respond with the specified status code and body message.
	 * 
	 * @param status
	 *            The HTTP response status code
	 * @param message
	 *            The message to add to the body.
	 */
	private void respondWith(Status status, String message) throws JSONException {
		setStatus(status);
		final JSONObject body = new JSONObject();
		body.put("error", message);
		final JsonRepresentation entity = new JsonRepresentation(body);
		getResponse().setEntity(entity);
	}
}
