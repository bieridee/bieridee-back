package ch.hsr.bieridee.domain;

import ch.hsr.bieridee.domain.interfaces.IDomain;

/**
 * Class representing a Tag.
 */
public class Tag implements IDomain {
	private long id;
	private String name;

	/**
	 * @param id
	 *            The id of the tag
	 * @param name
	 *            String containing the name of the Tag
	 */
	public Tag(long id, String name) {
		this.setId(id);
		this.setName(name);
	}

	public long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Tag id: "+ this.id +", Tag name: " + this.name;
	}

	@Override
	public int hashCode() {
		return (Long.toString(this.id) + this.name).hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Tag)) {
			return false;
		}
		final Tag t = (Tag) o;
		return new Long(this.getId()).equals(t.getId());
	}

}
