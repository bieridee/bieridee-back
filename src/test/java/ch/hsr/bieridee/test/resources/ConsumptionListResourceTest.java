package ch.hsr.bieridee.test.resources;

import java.util.List;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;

import ch.hsr.bieridee.config.NodeType;
import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BeerModel;
import ch.hsr.bieridee.models.ConsumptionModel;
import ch.hsr.bieridee.models.UserModel;
import ch.hsr.bieridee.utils.Cypher;
import ch.hsr.bieridee.utils.Cypherqueries;
import ch.hsr.bieridee.utils.DBUtil;
import ch.hsr.bieridee.utils.NodeProperty;

/**
 * Tests for the beer rating.
 * 
 */
public class ConsumptionListResourceTest extends ResourceTest {

	/**
	 * Tests the creation of a rating.
	 */
	@Test
	public void createRating() {
		final JSONObject rating = new JSONObject();
		final ClientResource cl = new ClientResource(Res.API_URL + "/beers/30/consumptions/alki"); //alki is the username of jonas
		final Representation rep = new StringRepresentation(rating.toString(), MediaType.APPLICATION_JSON);
		cl.post(rep);

		final List<Node> consumptionNodes = DBUtil.getTimeLine(1);
		final Node consumptionNode = consumptionNodes.get(0);
		Assert.assertEquals(NodeType.CONSUMPTION, consumptionNode.getProperty(NodeProperty.TYPE));
		try {
			final Node beerNode = DBUtil.getNodeById(30);
			final Node userNode = DBUtil.getUserByName("alki");
			final BeerModel bm = new BeerModel(beerNode);
			final UserModel um = new UserModel(userNode);

			final ConsumptionModel cm = new ConsumptionModel(consumptionNode);
			Assert.assertEquals(bm.getDomainObject().getName(), cm.getDomainObject().getBeer().getName());
			Assert.assertEquals(um.getUsername(), cm.getDomainObject().getUser().getUsername());
		} catch (NotFoundException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (WrongNodeTypeException e) {
			Assert.fail();
			e.printStackTrace();
		}
	}

	@Test
	public void getChronologicalConsumptions() {
	final	List<Node> consumptions = Cypher.executeAndGetNodes(Cypherqueries.GET_ALL_CONSUMPTIONS, "Action");
		System.out.println("Consumptions in the database:");
		for (Node n : consumptions) {
			try {
				final ConsumptionModel cm = new ConsumptionModel(n);
				Assert.assertEquals(cm.getNode().getProperty("type"), NodeProperty.Consumption.TYPE);
				System.out.println(cm);
			} catch (NotFoundException e) {
				e.printStackTrace();
			} catch (WrongNodeTypeException e) {
				e.printStackTrace();
			}

		}
	}
}
