package ch.hsr.bieridee.domain;

import ch.hsr.bieridee.domain.interfaces.IDomain;

/**
 * Class representing a Beertype.
 * 
 * @author cfaessle 
 */
public class Beertype implements IDomain {
	private long id;
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
	
	/**
	 * Creates a Beetype.
	 * 
	 * @param id Id of the Beertype
	 * @param name Name of the Beertype
	 * @param description
	 *            String describing the Beertype.
	 */
	public Beertype(long id, String name, String description) {
		this.id = id;
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
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Name: " + this.getName() + ",Description: "
				+ this.getDescription();
	}
}
