package ch.hsr.bieridee.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;

import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BeerModel;
import ch.hsr.bieridee.models.RatingModel;
import ch.hsr.bieridee.models.TagModel;
import ch.hsr.bieridee.models.UserModel;
import ch.hsr.bieridee.utils.DBUtil;

/**
 * Tests DBUtil functionality.
 * 
 */
public class DBUtilTest extends DBTest {

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
			Assert.fail();
			e.printStackTrace();
		} catch (WrongNodeTypeException e) {
			Assert.fail();
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
		final Node beerNode = DBUtil.getBeerByName(beerName);
		System.out.println("Not existing node:" + beerNode);
		new BeerModel(beerNode);
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
			bm1 = new BeerModel(31);
			bm2 = new BeerModel(32);
			bm3 = new BeerModel(30);
		} catch (NotFoundException e1) {
			Assert.fail();
			e1.printStackTrace();
		} catch (WrongNodeTypeException e1) {
			Assert.fail();
			e1.printStackTrace();
		}

		final Node tagNode = DBUtil.getTagByName("wuerzig");
		TagModel tm = null;
		try {
			tm = new TagModel(tagNode);
		} catch (NotFoundException e) {
			Assert.fail();
			e.printStackTrace();
		} catch (WrongNodeTypeException e) {
			Assert.fail();
			e.printStackTrace();
		}

		List<BeerModel> beerModels = null;
		try {
			beerModels = BeerModel.getAll(tm.getId());
		} catch (WrongNodeTypeException e) {
			Assert.fail();
			e.printStackTrace();
		}

		Assert.assertTrue(beerModels.contains(bm1));
		Assert.assertTrue(beerModels.contains(bm2));
		Assert.assertTrue(!beerModels.contains(bm3));

	}

	/**
	 * Tests if only the most recent rating is linked to the Active Index. Tests if the creation of new rating (to a
	 * already rated beer from the same user) is linked correctly.
	 */
	@Test
	public void getMostRecentRatingforUserANDcrateNewRatingForAlreadyRatedBeer() {
		getMostRecentRatingForUser();
		createNewRatingforAlreadyRatedBeer();
	}

	private void getMostRecentRatingForUser() {
		final Node activeRating = DBUtil.getActiveUserRatingForBeer(28, "saeufer");
		System.out.println("active rating dbtest: " + activeRating);
		RatingModel rm;
		try {
			rm = new RatingModel(activeRating);
			System.out.println("DOmain object: " + rm.getBeer().getDomainObject());
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (WrongNodeTypeException e) {
			e.printStackTrace();
		}
		Assert.assertEquals(59L, activeRating.getId());
	}

	private void createNewRatingforAlreadyRatedBeer() {
		UserModel um = null;
		BeerModel bm = null;
		try {
			bm = new BeerModel(28);
			um = new UserModel("saeufer");
		} catch (NotFoundException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (WrongNodeTypeException e) {
			e.printStackTrace();
			Assert.fail();
		}
		RatingModel.create(3, bm, um);
		final Node activeRating = DBUtil.getActiveUserRatingForBeer(28, "saeufer");
		System.out.println("new active rating: " + activeRating);
		Assert.assertNotSame(activeRating.getId(), 59L);
	}

}
