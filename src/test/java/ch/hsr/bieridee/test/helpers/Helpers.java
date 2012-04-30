package ch.hsr.bieridee.test.helpers;

import ch.hsr.bieridee.config.Config;

/**
 * Collection of helper classes used in tests. 
 */
public final class Helpers {
	private Helpers() {
		// Do not instantiate
	}
	
	/**
	 * Builds the internal resource URI. Used for testing HTTP requests.
	 * @param uriPath The URI Path with a leading /.
	 * @return Full URI String.
	 */
	public static String buildResourceUri(String uriPath) {
		return "http://" + Config.API_HOST + ":" + Config.API_PORT + uriPath;
	}
}
