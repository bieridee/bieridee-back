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

	public String getPicture() {
		return this.picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Description; " + this.description + ",Picture: " + this.picture;
	}

}
