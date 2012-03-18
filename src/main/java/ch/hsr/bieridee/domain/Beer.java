package ch.hsr.bieridee.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Beer {
	public String name;
	public String brand;
	public String picture;
	public List<Tag> tags;
	public Beersort beerSort;
	public int id;

	public Beer(int id, String name, String brand, String picture,
			List<Tag> tags, Beersort sort) {
		this.tags = tags;
		this.brand = brand;
		this.picture = picture;
		this.beerSort = sort;
		this.name = name;
		this.id = id;
	}

	@Override
	public String toString() {
		String s = "ID: " + id + "\nBeername: " + name + "\nMarke: " + brand
				+ "\nBild: " + picture + "\nTags: " + tags.toString()
				+ "\nSorte: " + beerSort.description;

		return s;
	}
}
