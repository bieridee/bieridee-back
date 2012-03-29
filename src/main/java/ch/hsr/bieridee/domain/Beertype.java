package ch.hsr.bieridee.domain;

/**
 * @author chrigi Class representing a Beertype.
 */
public class Beertype {
	private String name;
	private String description;

	/**
	 * @param description
	 *            String describing the Beertype.
	 */
	public Beertype(String description) {
		this.setDescription(description);
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
