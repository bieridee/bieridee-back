package ch.hsr.bieridee.domain;

/**
 * @author chrigi Class representing a Breweryprofile.
 */
public class BreweryProfile {
	/**
	 * @param picture
	 *            path to the picture of the Brewery.
	 * @param description
	 *            String describing the Brewery.
	 */
	public BreweryProfile(String picture, String description) {
		this.picture = picture;
		this.description = description;
	}

	private String picture;
	private String description;

	String getPicture() {
		return this.picture;
	}

	void setPicture(String picture) {
		this.picture = picture;
	}

	String getDescription() {
		return this.description;
	}

	void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "BreweryProfile, description " + this.description
				+ "\npicture: " + this.picture;
	}

}
