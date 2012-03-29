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
	 * @param emailAdress
	 *            Email Adress of the User.
	 */
	public User(String username, String password, String firstName,
			String lastName, String emailAdress) {
		this.setUsername(username);
		this.setPassword(password);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setEmailAdress(emailAdress);
	}

	private String password;
	private String firstName;
	private String lastName;
	private String emailAdress;

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFullName() {
		return this.firstName + " " + this.lastName;
	}

	@Override
	public String toString() {
		return "Firstname: " + this.getFirstName() + ",Lastname: "
				+ this.getLastName() + ",Email: " + getEmailAdress();
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	private String getEmailAdress() {
		return this.emailAdress;
	}

	public void setEmailAdress(String emailAdress) {
		this.emailAdress = emailAdress;
	}

}
