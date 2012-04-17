package ch.hsr.bieridee.domain;

import ch.hsr.bieridee.domain.interfaces.IDomain;

/**
 * Class representing a Brewery.
 */
public class Brewery implements IDomain {
	private long id;
	private String name;
	private String size;
	private String description;
	private String picture;

	/**
	 * @param name
	 *            Name of the Brewery.
	 * @param size
	 *            Size of the brewery.
	 * @param description
	 *            Description of the brewery.
	 * @param picture
	 *            Path to the picture.
	 */
	public Brewery(String name, String size, String description, String picture) {
		this.name = name;
		this.description = description;
		this.picture = picture;
		this.size = size;
	}
	
	/**
	 * @param id
	 *            ID of the brewery
	 * @param name
	 *            Name of the Brewery.
	 * @param size
	 *            Size of the brewery.
	 * @param description
	 *            Description of the brewery.
	 * @param picture
	 *            Path to the picture.
	 */
	public Brewery(long id, String name, String size, String description, String picture) {
		this(name, size, description, picture);
		this.id = id;
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	public String getSize() {
		return this.size;
	}
	
	public void setSize(String size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "Name: " + this.getName() + ", Size: " + this.size + ", Description: " + this.description;
	}

}
