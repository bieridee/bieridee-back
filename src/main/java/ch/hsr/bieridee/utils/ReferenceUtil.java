package ch.hsr.bieridee.utils;

import ch.hsr.bieridee.config.Config;

/**
 * Utility to create references.
 * 
 * @author jfurrer
 *
 */
public final class ReferenceUtil {
	
	private ReferenceUtil() {
		// do not instantiate.
	}
	
	/**
	 * Returns the resource URI for the beertpye with the given id.
	 * @param id The id of the beertype
	 * @return The URI of the beertype with the given id
	 */
	public static String getBeertypeURI(long id) {
		return Config.BEERTYPE_RESOURCE + "/" + id;
	}
	
	
	
	
}
