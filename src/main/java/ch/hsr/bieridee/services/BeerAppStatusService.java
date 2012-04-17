package ch.hsr.bieridee.services;

import org.neo4j.graphdb.NotFoundException;
import org.restlet.data.Status;
import org.restlet.resource.Resource;
import org.restlet.service.StatusService;

import ch.hsr.bieridee.exceptions.WrongNodeTypeException;

/**
 * Custom StatusService for the BeerApp.
 * 
 */
public class BeerAppStatusService extends StatusService {

	/**
	 * Creates an always enabled instance of the StatusService.
	 */
	public BeerAppStatusService() {
		super(true);
	}

	@Override
	public Status getStatus(Throwable throwable, Resource resource) {
		Status returnStatus = null;
		final Class<? extends Throwable> clazz = throwable.getCause().getClass();

		if (clazz.equals(WrongNodeTypeException.class)) {
			returnStatus = Status.CLIENT_ERROR_NOT_FOUND;
		} else if (clazz.equals(NotFoundException.class)) {
			returnStatus = Status.CLIENT_ERROR_NOT_FOUND;
		} else {
			returnStatus = super.getStatus(throwable, resource);
		}

		return returnStatus;
	}

}
