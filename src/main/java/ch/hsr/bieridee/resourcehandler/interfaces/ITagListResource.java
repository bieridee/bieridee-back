package ch.hsr.bieridee.resourcehandler.interfaces;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;

/**
 * Interface for the tag list resource.
 *
 */
public interface ITagListResource {
	
	/**
	 * Gets a list of tags.
	 * @return Representation of the tag list
	 */
	@Get
	Representation retrieve();
}
