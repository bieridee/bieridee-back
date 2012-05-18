package ch.hsr.bieridee.test.http;

import ch.hsr.bieridee.test.http.requestprocessors.AcceptRequestProcessor;
import ch.hsr.bieridee.test.http.requestprocessors.HMACAuthRequestProcessor;

/**
 * A factory that returns a default HttpHelper with support
 * for HMAC authentication and JSON accept header.
 */
public final class AuthJsonHttp {

	private AuthJsonHttp() {
		// Do not instantiate
	}

	/**
	 * Create a new HttpHelper instance, add the AcceptRequestProcessor
	 * and the HMACAuthRequestProcessor to it and return it.
	 * @return HttpHelper instance
	 */
	public static HttpHelper create() {
		final HttpHelper httpHelper = new HttpHelper();
		httpHelper.addRequestProcessor(new AcceptRequestProcessor(AcceptRequestProcessor.ContentType.JSON));
		httpHelper.addRequestProcessor(new HMACAuthRequestProcessor());
		return httpHelper;
	}

	/**
	 * Create a new HttpHelper instance, add the AcceptRequestProcessor
	 * and the HMACAuthRequestProcessor with cusotm login data to it
	 * and return it.
	 * @return HttpHelper instance
	 */
	public static HttpHelper create(String username, String password) {
		final HttpHelper httpHelper = new HttpHelper();
		httpHelper.addRequestProcessor(new AcceptRequestProcessor(AcceptRequestProcessor.ContentType.JSON));
		httpHelper.addRequestProcessor(new HMACAuthRequestProcessor(username, password));
		return httpHelper;
	}
}
