package ch.hsr.bieridee.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;

import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BeerModel;
import ch.hsr.bieridee.models.BeertypeModel;
import ch.hsr.bieridee.models.BreweryModel;
import ch.hsr.bieridee.models.TagModel;
import ch.hsr.bieridee.utils.DBUtil;

/**
 * Tests the <code>BeerModel</code>.
 */
public class BeerModelTest extends DBTest {

	/**
	 * TEST.
	 * 
	 * @throws WrongNodeTypeException
	 */

	@Test
	public void getBeersByTag() {

		BeerModel bm1 = null;
		BeerModel bm2 = null;
		try {
			bm1 = new BeerModel(36);
			bm2 = new BeerModel(32);
		} catch (NotFoundException e1) {
			e1.printStackTrace();
		} catch (WrongNodeTypeException e1) {
			e1.printStackTrace();
		}

		final Node tagNode = DBUtil.getTagByName("wuerzig");
		TagModel tm = null;
		try {
			tm = new TagModel(tagNode);
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (WrongNodeTypeException e) {
			e.printStackTrace();
		}

		List<BeerModel> beerModels = null;
		try {
			beerModels = BeerModel.getAll(tm.getId());
		} catch (WrongNodeTypeException e) {
			e.printStackTrace();
		}

		Assert.assertTrue(beerModels.contains(bm1));
		Assert.assertTrue(beerModels.contains(bm2));
	}

	/**
	 * Tests the creation of a new beer. (thats why the test is called 'createNewBeer').
	 */
	@Test
	public void createNewBeer() {
		final String name = "Leermond Bier";
		final String brand = "Appenzeller Bier";
		BeertypeModel beertypeModel = null;
		BreweryModel breweryModel = null;
		try {
			beertypeModel = new BeertypeModel(22);
			breweryModel = new BreweryModel(71);
		} catch (NotFoundException e) {
			Assert.fail();
			e.printStackTrace();
		} catch (WrongNodeTypeException e) {
			Assert.fail();
			e.printStackTrace();
		}
		Assert.assertNotNull(beertypeModel);
		Assert.assertNotNull(breweryModel);

		final BeerModel newBeer = BeerModel.create(name, brand, beertypeModel, breweryModel);
		BeerModel checkBeer = null;
		try {
			checkBeer = new BeerModel(newBeer.getNode());
		} catch (NotFoundException e) {
			Assert.fail();
			e.printStackTrace();
		} catch (WrongNodeTypeException e) {
			Assert.fail();
			e.printStackTrace();
		}
		Assert.assertNotNull(checkBeer);
		try {
			Assert.assertEquals(name, checkBeer.getName());
			Assert.assertEquals(brand, checkBeer.getBrand());
			Assert.assertEquals(breweryModel.getId(), checkBeer.getBrewery().getId());
			Assert.assertEquals(beertypeModel.getId(), checkBeer.getBeertype().getId());
		} catch (NotFoundException e) {
			Assert.fail();
			e.printStackTrace();
		} catch (WrongNodeTypeException e) {
			Assert.fail();
			e.printStackTrace();
		}
	}
}
