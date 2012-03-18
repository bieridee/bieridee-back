package ch.hsr.bieridee.domain;

public class Tag {
	String description;

	public Tag(String s) {
		this.description = s;
	}

	@Override
	public String toString() {
		return this.description;
	}
}
