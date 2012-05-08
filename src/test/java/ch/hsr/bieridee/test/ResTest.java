//CHECKSTYLE:OFF
package ch.hsr.bieridee.test;

import junit.framework.Assert;

import org.junit.Test;
import org.neo4j.graphdb.NotFoundException;

import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BeerModel;

public class ResTest extends DBTest {

	@Test
	public void beerUriTest() {
		BeerModel bm = null;
		try {
			bm = new BeerModel(30);
		} catch (NotFoundException e) {
			Assert.fail();
			e.printStackTrace();
		} catch (WrongNodeTypeException e) {
			Assert.fail();
			e.printStackTrace();
		}
		Assert.assertNotNull(bm);
		String URI = Res.PUBLIC_API_URL + Res.BEER_DOCUMENT.replaceAll("\\{.*\\}", "30");
		Assert.assertEquals(URI, Res.getResourceUri(bm));
	}

}
