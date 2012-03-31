package ch.hsr.bieridee.resourcehandler;

import java.util.LinkedList;
import java.util.List;

import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.Main;
import ch.hsr.bieridee.domain.Beer;
import ch.hsr.bieridee.domain.Beertype;
import ch.hsr.bieridee.domain.Tag;
import ch.hsr.bieridee.resourcehandler.interfaces.IBierListResource;

/**
 * @author chrigi ServerResource for getting a List of Beers.
 */
public class BeerListResource extends ServerResource implements
		IBierListResource {

	@Override
	public List<Beer> retrieve() {
		
		

		final List<Beer> beers = new LinkedList<Beer>();

		for (int i = 0; i < 5; ++i) {

			final List<Tag> b1Tags = new LinkedList<Tag>();
			b1Tags.add(new Tag("huren geil"));
			b1Tags.add(new Tag("lecker"));
			b1Tags.add(new Tag("gruusig"));
			Beer b1 = new Beer("Feldschlösschen", "Feldschlösschen",
					"feld.jpg", b1Tags, new Beertype("Lagerbier"));
			beers.add(b1);
		}
		return beers;
	}

}