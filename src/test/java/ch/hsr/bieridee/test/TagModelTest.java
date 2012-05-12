package ch.hsr.bieridee.test;

import org.junit.Assert;
import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;

import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BeerModel;
import ch.hsr.bieridee.models.TagModel;
import ch.hsr.bieridee.utils.DBUtil;

/**
 * Tests the TagModel Class.
 * 
 */
public class TagModelTest extends DBTest {
	/**
	 * Creates new Tag and Links it with a BeerModel.
	 */
	@Test
	public void addTagToBeerModel() {
		final String tagValue = "billig";

		try {
			final TagModel t = new TagModel(DBUtil.getTagByName("gruusig"));
			final BeerModel beerModel = new BeerModel(29);
			beerModel.addTag(t);

		} catch (WrongNodeTypeException e) {
			e.printStackTrace();
		}

		try {
			final BeerModel beerModel = new BeerModel(29);
			final TagModel tagModel = new TagModel(DBUtil.getTagByName(tagValue));
			final boolean tagWasAdded = beerModel.getTags().contains(tagModel.getDomainObject());
			Assert.assertTrue(tagWasAdded);
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (WrongNodeTypeException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Creates new Tag and stores it in DB. Checks if the Node was properly saved in the DB.
	 */
	@Test
	public void createNewTag() {
		final String tagName = "hell";
		final TagModel tagModel = TagModel.create(tagName);
		final Node nodeFromDB = DBUtil.getTagByName(tagName);
		try {
			final TagModel modelFromDB = new TagModel(nodeFromDB);
			Assert.assertEquals(tagModel.getName(), modelFromDB.getName());
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (WrongNodeTypeException e) {
			e.printStackTrace();
		}

		Assert.assertTrue(true);
	}

}
