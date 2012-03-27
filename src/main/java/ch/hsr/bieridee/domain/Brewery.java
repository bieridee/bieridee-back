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

	private BrewerySize getBrewerySize() {
		return this.brewerySize;
	}

	private void setBrewerySize(BrewerySize brewerySize) {
		this.brewerySize = brewerySize;
	}

	private String getName() {
		return this.name;
	}

	private void setName(String name) {
		this.name = name;
	}

}
