package ch.hsr.bieridee.domain;

/**
 * @author chrigi Class representing a Brewery.
 */
public class Brewery {
	private String name;
	private BreweryProfile breweryProfile;
	private BrewerySize brewerySize;

	/**
	 * @param breweryProfile
	 *            Profile associated with the Brewery.
	 * @param brewerySize
	 *            Size of the brewery.
	 */
	public Brewery(BreweryProfile breweryProfile, BrewerySize brewerySize) {
		super();
		this.breweryProfile = breweryProfile;
		this.brewerySize = brewerySize;
	}

	BreweryProfile getBreweryProfile() {
		return this.breweryProfile;
	}

	void setBreweryProfile(BreweryProfile breweryProfile) {
		this.breweryProfile = breweryProfile;
	}

	public BrewerySize getBrewerySize() {
		return this.brewerySize;
	}

	public void setBrewerySize(BrewerySize brewerySize) {
		this.brewerySize = brewerySize;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Name: " + this.getName() + ",Profile: "
				+ this.getBreweryProfile() + ",Size: " + this.brewerySize;
	}

}
