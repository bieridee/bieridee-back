package ch.hsr.bieridee.resourcehandler;

import java.util.List;

import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.AbstractActionModel;
import ch.hsr.bieridee.models.TimelineModel;
import ch.hsr.bieridee.resourcehandler.interfaces.IReadOnlyResource;

/**
 * Timeline resource.
 *
 */
public class TimelineResource extends ServerResource implements IReadOnlyResource {

	@Override
	public Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException {
		
		final List<AbstractActionModel> actionModels = TimelineModel.getAll();
		
		final AbstractActionModel[] actionModelArray = actionModels.toArray(new AbstractActionModel[actionModels.size()]);
		final JacksonRepresentation<AbstractActionModel[]> actionsRep = new JacksonRepresentation<AbstractActionModel[]>(actionModelArray);
		actionsRep.setObjectMapper(Config.getObjectMapper());
		
		return actionsRep;
	}

}
