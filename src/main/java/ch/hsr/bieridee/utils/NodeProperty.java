package ch.hsr.bieridee.utils;

/**
 * Class containing constants of Node properties.
 * 
 */
public final class NodeProperty {

	private NodeProperty() {

	}

	public static final String TYPE = "type";

	/**
	 * User Node specific properties.
	 * 
	 */
	public static final class User {
		public static final String USERNAME = "username";
		public static final String EMAIL = "email";
		public static final String PRENAME = "prename";
		public static final String SURNAME = "surname";
		public static final String PASSWORD = "password";
	}

	/**
	 * Beer Node specific properties.
	 * 
	 */
	public static final class Beer {
		public static final String NAME = "name";
		public static final String BRAND = "brand";
		public static final String IMAGE = "image";
	}

	/**
	 * Beertype specific properties.
	 * 
	 */
	public static final class Beertype {
		public static final String NAME = "name";
		public static final String DESCRIPTION = "description";
	}

	/**
	 * Tag specific properties.
	 * 
	 */
	public static final class Tag {
		public static final String NAME = "name";
		public static final String TYPE = "type";
	}
}
