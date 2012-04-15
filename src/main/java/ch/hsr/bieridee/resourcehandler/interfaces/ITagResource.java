package ch.hsr.bieridee.resourcehandler.interfaces;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;

/**
 * Interface for the tag resource.
 *
 */
public interface ITagResource {
	
	/**
	 * Gets a tag.
	 * @return The representation of a tag
	 */
	@Get
	Representation retrieve();
}
