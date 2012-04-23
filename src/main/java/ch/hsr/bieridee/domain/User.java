package ch.hsr.bieridee.domain;

import ch.hsr.bieridee.domain.interfaces.IDomain;

/**
 * Class representing a User.
 */
public class User implements IDomain {
	private String prename;
	private String surname;
	private String username;
	private String password;
	private String email;

	/**
	 * @param username
	 *            Username of the User.
	 * @param password
	 *            Passwort of the User.
	 * @param prename
	 *            name of the User.
	 * @param surname
	 *            Prename of the User.
	 * @param email
	 *            Email Adress of the User.
	 */
	public User(String username, String password, String prename, String surname, String email) {
		this.setUsername(username);
		this.setPassword(password);
		this.setPrename(prename);
		this.setSurname(surname);
		this.setEmail(email);
	}

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

	public String getPrename() {
		return this.prename;
	}

	public void setPrename(String prename) {
		this.prename = prename;
	}

	public String getFullName() {
		return this.prename + " " + this.surname;
	}

	@Override
	public String toString() {
		return "Firstname: " + this.getPrename() + ",Lastname: " + this.getSurname() + ",Email: " + getEmail();
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
