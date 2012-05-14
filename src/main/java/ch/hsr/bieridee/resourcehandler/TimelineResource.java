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
	public static final int ITEM_COUNT_DEFAULT = 12;

	@Override
	public Representation retrieve() throws WrongNodeTypeException, NotFoundException {
		final List<AbstractActionModel> actionModels;
		final String usernameParam = getQuery().getFirstValue(Res.TIMELINE_FILTER_PARAMETER_USER);
		final int nOfItems = getNumberOfItemsParam();
		final int page = getPageNumberParam();

		if (usernameParam == null) {
			actionModels = TimelineModel.getAll(nOfItems, page * nOfItems);
		} else if (!UserModel.doesUserExist(usernameParam)) { // Invalid Username
			throw new NotFoundException("Username invalid");
		} else {
			actionModels = TimelineModel.getAllForUser(usernameParam, nOfItems, page * nOfItems);
		}

		final AbstractActionModel[] actionModelArray = actionModels.toArray(new AbstractActionModel[actionModels.size()]);
		final JacksonRepresentation<AbstractActionModel[]> actionsRep = new JacksonRepresentation<AbstractActionModel[]>(actionModelArray);
		actionsRep.setObjectMapper(Config.getObjectMapper());

		return actionsRep;
	}

	private int getPageNumberParam() {
		final String pageNumber = getQuery().getFirstValue(Res.TIMELINE_PAGE_PARAMETER);
		if (pageNumber != null) {
			return Integer.parseInt(pageNumber);
		}
		return 0;

	}

	private int getNumberOfItemsParam() {
		final String nOfItemsParam = getQuery().getFirstValue(Res.TIMELINE_LIST_SIZE_PARAMETER);
		if (nOfItemsParam != null) {
			return Integer.parseInt(nOfItemsParam);
		}
		return ITEM_COUNT_DEFAULT;

	}
}
