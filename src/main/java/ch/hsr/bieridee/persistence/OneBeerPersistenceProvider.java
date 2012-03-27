package ch.hsr.bieridee.persistence;

import java.util.LinkedList;
import java.util.Random;

import ch.hsr.bieridee.domain.Beer;
import ch.hsr.bieridee.domain.Beertype;
import ch.hsr.bieridee.domain.Tag;

public class OneBeerPersistenceProvider implements IPersistenceProvider {

	private Beer createDummyBier() {
		String name = "Feldschl�ssen";
		Tag t1 = new Tag("delicious");
		Tag t2 = new Tag("drinky");
		Beertype sort = new Beertype(
				"Weiss Bier. Wird haupts�chlich von M�nchen in Einsiedeln gebraut");
		LinkedList<Tag> tags = new LinkedList<Tag>();
		tags.add(t1);
		tags.add(t2);
		int id = new Random().nextInt();
		String brand = "Felschl�sschen";
		String picture = "/img/" + id + ".jpg";
		Beer b = new Beer(id, name, brand, picture, tags, sort);
		return b;
	}

	@Override
	public Beer getBierById(int id) {
		return createDummyBier();
	}

	@Override
	public void saveBier(Beer b) {
		System.out.println("Beer is gonna be saved");

	}

	@Override
	public Beer getBierByName(String name) {
		return this.createDummyBier();

	}

}
