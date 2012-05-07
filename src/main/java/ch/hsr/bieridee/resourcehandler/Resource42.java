package ch.hsr.bieridee.resourcehandler;

import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.resourcehandler.interfaces.IReadOnlyResource;

/**
 * Testing resource, returns 42 on GET requests.
 * 
 */
public class Resource42 extends ServerResource implements IReadOnlyResource {

	@Override
	public Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException {
		final StringRepresentation theAnsweris42 = new StringRepresentation("42");
		return theAnsweris42;
	}

}
