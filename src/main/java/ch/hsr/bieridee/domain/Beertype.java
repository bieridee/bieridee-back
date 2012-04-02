package ch.hsr.bieridee.domain;

/**
 * Class representing a Beertype.
 * 
 * @author chrigi 
 */
public class Beertype {
	private String name;
	private String description;

	/**
	 * Creates a Beetype.
	 * 
	 * @param name Name of the Beertype
	 * @param description
	 *            String describing the Beertype.
	 */
	public Beertype(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Name: " + this.getName() + ",Description: "
				+ this.getDescription();
	}
}
