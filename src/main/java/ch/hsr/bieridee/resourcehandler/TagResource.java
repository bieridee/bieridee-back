package ch.hsr.bieridee.resourcehandler;

import org.apache.commons.lang.NotImplementedException;
import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.TagModel;
import ch.hsr.bieridee.resourcehandler.interfaces.IStoreResource;

/**
 * Tag Resource.
 *
 */
public class TagResource extends ServerResource implements IStoreResource {
	
	private long tagId;
	
	@Override
	public void doInit() {
		this.tagId = Long.parseLong((String) getRequestAttributes().get(Res.TAG_REQ_ATTR));
	}
	
	@Override
	public Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException {
		final TagModel tagModel = new TagModel(this.tagId);
		
		final JacksonRepresentation<TagModel> tagJacksonRep = new JacksonRepresentation<TagModel>(tagModel);
		tagJacksonRep.setObjectMapper(Config.getObjectMapper());
		
		return tagJacksonRep;
	}

	@Override
	public void store(Representation rep) {
		throw new NotImplementedException(); // TODO
	}

	@Override
	public void remove(Representation rep) {
		throw new NotImplementedException(); // TODO
	}

}
