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
public class BeerListResource extends ServerResource implements IBierListResource {

	@Override
	public List<Beer> retrieve() {

		final List<Beer> beers = new LinkedList<Beer>();
		final List<Node> beerNodes = DBUtil.getBeerNodeList();
		for (Node beerNode : beerNodes) {
			final BeerModel bm = new BeerModel(beerNode.getId());
			beers.add(bm.getDomainObject());
		}

		return beers;
	}

}