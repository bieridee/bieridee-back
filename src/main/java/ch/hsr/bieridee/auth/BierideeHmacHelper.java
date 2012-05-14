package ch.hsr.bieridee.auth;

import org.restlet.Response;
import org.restlet.data.ChallengeRequest;
import org.restlet.data.ChallengeScheme;
import org.restlet.engine.header.ChallengeWriter;
import org.restlet.engine.header.Header;
import org.restlet.engine.security.AuthenticatorHelper;
import org.restlet.util.Series;

/**
 * Implements the Bieridee HMAC-SHA256 authentication.
 */
public class BierideeHmacHelper extends AuthenticatorHelper {

	public static final ChallengeScheme SCHEME = new ChallengeScheme(
			"BIERIDEE_HMAC", // name
			"HMAC_SHA256", // technical name
			"Bieridee HMAC-SHA256 based authentication" // description
	);

	public BierideeHmacHelper() {
		super(SCHEME, false, true);
	}

	/**
	 * Format the challenge request.
	 * @param cw ChallengeWriter
	 * @param challenge ChallengeRequest
	 * @param response HTTP Response
	 * @param httpHeaders HTTP Headers
	 */
	@Override
	public void formatRequest(ChallengeWriter cw, ChallengeRequest challenge, Response response, Series<Header> httpHeaders) {
		if (challenge.getRealm() != null) {
			cw.appendQuotedChallengeParameter("realm", challenge.getRealm());
		}
	}
}
