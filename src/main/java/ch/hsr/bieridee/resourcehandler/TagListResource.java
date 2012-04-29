package ch.hsr.bieridee.resourcehandler;

import java.util.List;

import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.TagModel;
import ch.hsr.bieridee.resourcehandler.interfaces.IReadOnlyResource;

/**
 * Tag list resource.
 *
 */
public class TagListResource extends ServerResource implements IReadOnlyResource {

	@Override
	public Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException {
		final List<TagModel> tagModels = TagModel.getAll();
				
		final TagModel[] tagModelArray = tagModels.toArray(new TagModel[tagModels.size()]);
		final JacksonRepresentation<TagModel[]> tagsJacksonRep = new JacksonRepresentation<TagModel[]>(tagModelArray);
		tagsJacksonRep.setObjectMapper(Config.getObjectMapper());
		
		return tagsJacksonRep;
	}

}
