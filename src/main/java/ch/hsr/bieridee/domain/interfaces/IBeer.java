package ch.hsr.bieridee.domain.interfaces;

import java.util.List;

import ch.hsr.bieridee.domain.Beertype;
import ch.hsr.bieridee.domain.Tag;

public interface IBeer {

	void setName(String name);

	String getName();

	void setBrand(String brand);

	String getBrand();

	void setPicture(String picture);

	String getPicture();

	void setTags(List<Tag> tags);

	List<Tag> getTags();

	void setBeertype(Beertype beerType);

	Beertype getBeertype();
}