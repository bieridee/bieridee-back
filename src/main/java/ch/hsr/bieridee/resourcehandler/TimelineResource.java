package ch.hsr.bieridee.resourcehandler;

import java.util.List;

import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.config.Res;
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
		String username;
		final List<AbstractActionModel> actionModels;
		final String usernameParam = getQuery().getFirstValue(Res.TIMELINE_FILTER_PARAMETER_USER);
		if(usernameParam == null){
			actionModels = TimelineModel.getAll();
		}
		else{
			actionModels = TimelineModel.getAllForUser(usernameParam);
		}
		
		final AbstractActionModel[] actionModelArray = actionModels.toArray(new AbstractActionModel[actionModels.size()]);
		final JacksonRepresentation<AbstractActionModel[]> actionsRep = new JacksonRepresentation<AbstractActionModel[]>(actionModelArray);
		actionsRep.setObjectMapper(Config.getObjectMapper());
		
		return actionsRep;
	}

}
