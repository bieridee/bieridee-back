package ch.hsr.bieridee.resourcehandler;


import ch.hsr.bieridee.auth.BierideeHmacHelper;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.UserModel;
import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Hex;
import org.neo4j.graphdb.NotFoundException;
import org.restlet.Request;
import org.restlet.data.ChallengeRequest;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Server resource controller to verify user credentials.
 */
public final class UserCredentialsResource extends ServerResource {

	private static final Logger LOG = Logger.getLogger(UserCredentialsResource.class);

	/**
	 * Verify user credentials.
	 *
	 * @return Representation
	 * @throws WrongNodeTypeException Thrown if UserModel constructor returns a node with the wrong nodetype.
	 * @throws NoSuchAlgorithmException Thrown if HMAC-SHA256 algorithm was not found.
	 * @throws InvalidKeyException Thrown if key for HMAC-SHA256 signing is invalid.
	 */
	@Post
	public Representation process() throws WrongNodeTypeException, NoSuchAlgorithmException, InvalidKeyException {
		// Get authorization header
		final Request request = getRequest();
		final Series reqHeaders = (Series) request.getAttributes().get("org.restlet.http.headers");
		final String reqAuthorization = reqHeaders.getFirstValue("Authorization");

		// Process authorization header
		final String[] reqAthorizationArray = reqAuthorization.split(":", 2);
		final String reqUsername = reqAthorizationArray[0];
		final String reqHMAC = reqAthorizationArray[1];

		// Get user model
		UserModel userModel = null;
		try {
			userModel = new UserModel(reqUsername);
		} catch (NotFoundException e) {
			LOG.error("User " + reqUsername + " not found!");
			return this.unauthorized();
		}

		// Get request information for HMAC calculation
		final String reqMethod = getMethod().getName();
		final String reqUri = request.getOriginalRef().getIdentifier(true);
		final String reqAccept = reqHeaders.getFirstValue("Accept");
		final String reqContentLength = reqHeaders.getFirstValue("Content-Length");
		final String reqBody = request.getEntityAsText();

		// Calculate HMAC (TODO: Auslagern in Guard?)
		final String hmacInputData = reqMethod + reqUri + reqAccept + reqContentLength + reqBody;
		final Key signingKey = new SecretKeySpec(userModel.getPassword().getBytes(), "HmacSHA256");
		final String calculatedHMAC = calculateHMAC(hmacInputData, signingKey);

		// Verify HMAC
		if (reqHMAC.equals(calculatedHMAC)) {
			return this.authorized();
		} else {
			return this.unauthorized();
		}
	}

	/**
	 * Calculate the HMAC-SHA256 for the given input data and the given signing key.
	 *
	 * @param hmacInputString The input string that should be hashed
	 * @param signingKey The signing key that is used to calculate the MAC
	 * @return The calculated HMAC-SHA256 string
	 * @throws NoSuchAlgorithmException Thrown if HMAC-SHA256 algorithm was not found.
	 * @throws InvalidKeyException Thrown if key for HMAC-SHA256 signing is invalid.
	 */
	private String calculateHMAC(String hmacInputString, Key signingKey) throws NoSuchAlgorithmException, InvalidKeyException {
		String calculatedHMAC = "";
		try {

			final Mac m = Mac.getInstance("HmacSHA256");
			m.init(signingKey);
			m.update(hmacInputString.getBytes());
			final byte[] macBytes = m.doFinal();
			calculatedHMAC = new String(Hex.encode(macBytes));
		} catch (NoSuchAlgorithmException e) {
			LOG.error("HmacSHA256 algorithm missing");
			throw e;
		} catch (InvalidKeyException e) {
			LOG.error("Invalid key for HmacSHA256 signing");
			throw e;
		}
		return calculatedHMAC;
	}

	/**
	 * Return a HTTP 401 response and set challenge request headers.
	 *
	 * @return null
	 */
	private Representation unauthorized() {
		final List<ChallengeRequest> list = new CopyOnWriteArrayList<ChallengeRequest>();
		list.add(new ChallengeRequest(BierideeHmacHelper.SCHEME));
		setChallengeRequests(list); // TODO does not work
		setStatus(Status.CLIENT_ERROR_UNAUTHORIZED);
		return null;
	}

	/**
	 * Return a HTTP 204 response.
	 * 
	 * @return null
	 */
	private Representation authorized() {
		setStatus(Status.SUCCESS_NO_CONTENT);
		return null;
	}
}
