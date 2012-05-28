package ch.hsr.bieridee.resourcehandler;

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

/**
 * Server resource controller to verify user credentials.
 */
public final class UserCredentialsResource extends ServerResource {

	/**
	 * Verify user credentials.
	 *
	 * As this resource is protected by the HmacSha256Verifier, it simply
	 * returns HTTP 204 if authentication was successful.
	 *
	 * @return Representation
	 */
	@Post
	public Representation process() {
		setStatus(Status.SUCCESS_NO_CONTENT);
		return null;
	}
}
