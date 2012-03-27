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
	 * @param firstName
	 *            name of the User.
	 * @param lastName
	 *            Prename of the User.
	 */
	public User(String username, String password, String firstName,
			String lastName) {
		super();
		this.setUsername(username);
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	private String password;
	private String firstName;
	private String lastName;
	private String emailAdress;

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

	String getFirstName() {
		return this.firstName;
	}

	void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFullName() {
		return this.firstName + " " + this.lastName;
	}

	@Override
	public String toString() {
		return "Firstname: " + this.getFirstName() + ",Lastname: "
				+ this.getLastName();
	}

	private String getLastName() {
		return this.lastName;
	}

	void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
