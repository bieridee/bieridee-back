package ch.hsr.bieridee.domain;

import ch.hsr.bieridee.domain.interfaces.IDomain;

/**
 * Class representing a Tag.
 */
public class Tag implements IDomain{
	private String name;

	/**
	 * @param name
	 *            String containing the name of the Tag.
	 */
	public Tag(String name) {
		this.setName(name);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Tag name: " + this.name;
	}

}
