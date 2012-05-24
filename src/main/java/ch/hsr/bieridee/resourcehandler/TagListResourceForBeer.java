package ch.hsr.bieridee.resourcehandler;

import org.neo4j.server.rest.web.NodeNotFoundException;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.resourcehandler.interfaces.IReadOnlyResource;

/**
 * Resource to retrieve all brands.
 * 
 */
public class TagListResourceForBeer extends ServerResource implements IReadOnlyResource {

	@Override
	public Representation retrieve() throws WrongNodeTypeException, NodeNotFoundException {
		this.redirectSeeOther("/tags?beerId=28");
		return null;
	}

}
