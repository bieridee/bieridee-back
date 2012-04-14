package ch.hsr.bieridee.domain;

import ch.hsr.bieridee.domain.interfaces.IDomain;

/**
 * @author chrigi Class representing a Tag.
 */
public class Tag implements IDomain{
	private String value;

	/**
	 * @param value
	 *            String containing the value of the Tag.
	 */
	public Tag(String value) {
		this.setValue(value);
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Tag value: " + this.value;
	}

}
