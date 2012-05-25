package ch.hsr.bieridee.domain;

import ch.hsr.bieridee.domain.interfaces.IDomain;

/**
 * Barcode domain object
 */
public class Barcode implements IDomain {

	private long id;
	private String code;
	private String format = "";

	public Barcode(String code) {
		this(code, null);
	}

	public Barcode(String code, String format) {
		this.setCode(code);
		if (format != null) {
			this.setFormat(format);
		}
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
}