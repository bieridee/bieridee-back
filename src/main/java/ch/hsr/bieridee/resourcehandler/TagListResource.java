package ch.hsr.bieridee.resourcehandler;

import java.util.List;

import org.neo4j.graphdb.Node;
import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.domain.Tag;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.TagModel;
import ch.hsr.bieridee.resourcehandler.interfaces.ITagListResource;
import ch.hsr.bieridee.utils.DBUtil;
import ch.hsr.bieridee.utils.DomainConverter;

/**
 * Tag list resource.
 *
 */
public class TagListResource extends ServerResource implements ITagListResource {

	@Override
	public Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException {
		final List<Node> tagNodes = DBUtil.getTagNodeList();
		final List<TagModel> tagModels = DomainConverter.createTagModelsFromList(tagNodes);
				
		final List<Tag> tagList = DomainConverter.extractDomainObjectFromModel(tagModels);
		final Tag[] tags = tagList.toArray(new Tag[tagList.size()]);
		final JacksonRepresentation<Tag[]> tagsJacksonRep = new JacksonRepresentation<Tag[]>(tags);
		tagsJacksonRep.setObjectMapper(Config.getObjectMapper());
		
		return tagsJacksonRep;
	}

}
