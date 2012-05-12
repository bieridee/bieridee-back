package ch.hsr.bieridee.resourcehandler;

import java.util.List;

import org.neo4j.graphdb.NotFoundException;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.AbstractActionModel;
import ch.hsr.bieridee.models.TimelineModel;
import ch.hsr.bieridee.models.UserModel;
import ch.hsr.bieridee.resourcehandler.interfaces.IReadOnlyResource;

/**
 * Timeline resource.
 * 
 */
public class TimelineResource extends ServerResource implements IReadOnlyResource {

	@Override
	public Representation retrieve() throws WrongNodeTypeException, NotFoundException {
		final String usernameParam = getQuery().getFirstValue(Res.TIMELINE_FILTER_PARAMETER_USER);
		final List<AbstractActionModel> actionModels;
		if (usernameParam == null) {
			actionModels = TimelineModel.getAll();
		} else if (!UserModel.doesUserExist(usernameParam)) { // Invalid Username
			throw new NotFoundException("Username invalid");
		} else {
			actionModels = TimelineModel.getAllForUser(usernameParam);
		}

		final AbstractActionModel[] actionModelArray = actionModels.toArray(new AbstractActionModel[actionModels.size()]);
		final JacksonRepresentation<AbstractActionModel[]> actionsRep = new JacksonRepresentation<AbstractActionModel[]>(actionModelArray);
		actionsRep.setObjectMapper(Config.getObjectMapper());

		return actionsRep;
	}
}
