package ch.hsr.bieridee.test;

import org.junit.Assert;
import org.junit.Test;
import org.neo4j.graphdb.NotFoundException;

import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BeerModel;
import ch.hsr.bieridee.models.TagModel;
import ch.hsr.bieridee.utils.DBUtil;

/**
 * Tests the <code>BeerModel</code>.
 * 
 * @author jfurrer
 * 
 */
public class BeerModelTest {

	/**
	 * TEST.
	 * 
	 * @throws WrongNodeTypeException
	 */
	@Test
	public void getNodeTest() throws WrongNodeTypeException {
		// final Node n = DBUtil.getNodeById(5);
		// final BeerModel bm = new BeerModel(5);
		// final Node beernode = bm.getNode();
		//
		// Assert.assertEquals(n, beernode);
	}
}
