package ch.hsr.bieridee.domain;

import java.util.List;

import ch.hsr.bieridee.domain.interfaces.IDomain;

/**
 * Class representing a Beer.
 */
public class Beer implements IDomain {
	private long id;
	private String name;
	private String brand;
	private String image;
	private List<Tag> tags;
	private Beertype beertype;
	private Brewery brewery;

	/**
	 * Creates a Beer.
	 * 
	 * @param name
	 *            Name of the Beer.
	 * @param brand
	 *            Brand of the Beer.
	 * @param picture
	 *            path to the Picture of the Beer.
	 * @param tags
	 *            <code>List</code> of Tags associated with this <code>Beer</code>.
	 * @param type
	 *            Beertype object.
	 * @param brewery
	 *            Brewery object.
	 * 
	 */
	public Beer(String name, String brand, String picture, List<Tag> tags, Beertype type, Brewery brewery) {
		this.setName(name);
		this.setBrand(brand);
		this.setPicture(picture);
		this.setTags(tags);
		this.setBeertype(type);
		this.setBrewery(brewery);
	}

	/**
	 * Creates a Beer.
	 * 
	 * @param id
	 *            Id of the Beer.
	 * @param name
	 *            Name of the Beer.
	 * @param brand
	 *            Brand of the Beer.
	 * @param picture
	 *            path to the Picture of the Beer.
	 * @param tags
	 *            <code>List</code> of Tags associated with this <code>Beer</code>.
	 * @param type
	 *            description of the beertype.
	 * @param brewery
	 *            Brewery object.
	 */
	public Beer(long id, String name, String brand, String picture, List<Tag> tags, Beertype type, Brewery brewery) {
		this(name, brand, picture, tags, type, brewery);
		this.setId(id);
	}

	@Override
	public String toString() {
		return "Beername: " + getName() + ",Brand: " + getBrand() + ",Picture: " + getPicture() + ",Tags: " + getTags().toString() + ",Type: " + getBeertype().getDescription();

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

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getPicture() {
		return this.image;
	}

	public void setPicture(String image) {
		this.image = image;
	}

	public List<Tag> getTags() {
		return this.tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public Beertype getBeertype() {
		return this.beertype;
	}

	public void setBeertype(Beertype beertype) {
		this.beertype = beertype;
	}
	
	public Brewery getBrewery() {
		return this.brewery;
	}

	public void setBrewery(Brewery brewery) {
		this.brewery = brewery;
	}
}
