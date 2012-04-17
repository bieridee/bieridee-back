package ch.hsr.bieridee.resourcehandler;

import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.domain.Tag;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.TagModel;
import ch.hsr.bieridee.resourcehandler.interfaces.ITagResource;

/**
 * Tag Resource.
 *
 */
public class TagResource extends ServerResource implements ITagResource {
	
	private String tagName;
	
	@Override
	public void doInit() {
		this.tagName = (String) getRequestAttributes().get(Res.TAG_REQ_ATTR);
	}
	
	@Override
	public Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException {
		final TagModel tm = new TagModel(this.tagName);
		
		final Tag tag = tm.getDomainObject();
		final JacksonRepresentation<Tag> tagJacksonRep = new JacksonRepresentation<Tag>(tag);
		tagJacksonRep.setObjectMapper(Config.getObjectMapper());
		
		return tagJacksonRep;
	}

}
