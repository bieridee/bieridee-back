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

	String getDescription() {
		return this.description;
	}

	void setDescription(String description) {
		this.description = description;
	}

	private String getName() {
		return this.name;
	}

	private void setName(String name) {
		this.name = name;
	}
}
