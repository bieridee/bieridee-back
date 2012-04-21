package ch.hsr.bieridee.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;
import org.neo4j.server.rest.web.NodeNotFoundException;

import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BeerModel;
import ch.hsr.bieridee.models.TagModel;
import ch.hsr.bieridee.utils.DBUtil;
import ch.hsr.bieridee.utils.DomainConverter;

/**
 * Tests DBUtil functionality.
 * 
 */
public class DBUtilTest {

	/**
	 * Tests if a beer could be obtained by name.
	 * 
	 */
	@Test
	public void getBeerByNameTest() {
		final String beerName = "Falken First Cool";
		BeerModel bm1 = null;
		final Node beerNode = DBUtil.getBeerByName(beerName);
		try {
			bm1 = new BeerModel(beerNode);
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (WrongNodeTypeException e) {
			e.printStackTrace();
		}

		Assert.assertNotNull(bm1);
		Assert.assertTrue(bm1.getName().equals(beerName));
	}

	/**
	 * @throws WrongNodeTypeException
	 * @throws NotFoundException
	 * 
	 */
	@Test(expected = NotFoundException.class)
	public void getNotExistingBeerByNameTest() throws NotFoundException, WrongNodeTypeException {
		final String beerName = "Chrigis Super Brew!";
		BeerModel bm1 = null;
		final Node beerNode = DBUtil.getBeerByName(beerName);
		System.out.println(beerNode);
		bm1 = new BeerModel(beerNode);
		Assert.assertNull(beerNode);
	}

	/**
	 * Test the Filter by Tag Function of DBUtil.
	 * 
	 */
	@Test
	public void getBeersByTag() {

		BeerModel bm1 = null;
		BeerModel bm2 = null;
		BeerModel bm3 = null;
		try {
			bm1 = new BeerModel(11);
			bm2 = new BeerModel(12);
			bm3 = new BeerModel(10);
		} catch (NotFoundException e1) {
			e1.printStackTrace();
		} catch (WrongNodeTypeException e1) {
			e1.printStackTrace();
		}

		final Node tagNode = DBUtil.getTagByName("suess");
		TagModel tm = null;
		try {
			tm = new TagModel(tagNode);
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (WrongNodeTypeException e) {
			e.printStackTrace();
		}

		final List<Node> beerNodes = DBUtil.getBeerNodeList(tm.getName());
		List<BeerModel> beerModels = null;
		try {
			beerModels = DomainConverter.createBeerModelsFromList(beerNodes);
		} catch (WrongNodeTypeException e) {
			e.printStackTrace();
		} catch (NodeNotFoundException e) {
			e.printStackTrace();
		}

		Assert.assertTrue(beerModels.contains(bm1));
		Assert.assertTrue(beerModels.contains(bm2));
		Assert.assertTrue(!beerModels.contains(bm3));

	}

}
