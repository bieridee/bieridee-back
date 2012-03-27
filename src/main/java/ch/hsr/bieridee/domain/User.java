package ch.hsr.bieridee.domain;

/**
 * @author chrigi Class representing a User.
 */
public class User {
	private String username;

	/**
	 * @param username
	 *            Username of the User.
	 * @param password
	 *            Passwort of the User.
	 * @param name
	 *            name of the User.
	 * @param prename
	 *            Prename of the User.
	 * @param language
	 *            Language of the User.
	 */
	public User(String username, String password, String name, String prename,
			String language) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.prename = prename;
		this.language = language;
	}

	private String password;
	private String name;
	private String prename;
	private String language;

	String getUsername() {
		return this.username;
	}

	void setUsername(String username) {
		this.username = username;
	}

	String getPassword() {
		return this.password;
	}

	void setPassword(String password) {
		this.password = password;
	}

	String getName() {
		return this.name;
	}

	void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Name: " + this.getName() + "\nPrename: " + this.getPrename()
				+ "\nLanguage: " + this.getLanguage();
	}

	private String getPrename() {
		return this.prename;
	}

	void setPrename(String prename) {
		this.prename = prename;
	}

	private String getLanguage() {
		return this.language;
	}

	private void setLanguage(String language) {
		this.language = language;
	}

}
