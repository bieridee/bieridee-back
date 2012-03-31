package ch.hsr.bieridee.resourcehandler;

import java.util.LinkedList;
import java.util.List;

import org.neo4j.graphdb.Node;
import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.domain.Beer;
import ch.hsr.bieridee.models.BeerModel;
import ch.hsr.bieridee.resourcehandler.interfaces.IBierListResource;
import ch.hsr.bieridee.utils.DBUtil;

/**
 * @author chrigi ServerResource for getting a List of Beers.
 */
public class BeerListResource extends ServerResource implements
		IBierListResource {

	@Override
	public List<Beer> retrieve() {
		
		final List<Beer> beers = new LinkedList<Beer>();
		final List<Node> beerNodes = DBUtil.getBeerNodeList();
		for(Node beerNode : beerNodes) {
			final BeerModel bm = new BeerModel(beerNode.getId());
			beers.add(bm.getDomainObject());
		}
		
//		for (int i = 0; i < 5; ++i) {
//
//			final List<Tag> b1Tags = new LinkedList<Tag>();
//			b1Tags.add(new Tag("huren geil"));
//			b1Tags.add(new Tag("lecker"));
//			b1Tags.add(new Tag("gruusig"));
//			Beer b1 = new Beer("Feldschlösschen", "Feldschlösschen",
//					"feld.jpg", b1Tags, new Beertype("Feldschlösschen", "Lagerbier"));
//			beers.add(b1);
//		}
		
		return beers;
	}

}