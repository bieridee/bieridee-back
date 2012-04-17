//CHECKSTYLE:OFF
package ch.hsr.bieridee.test;

import java.util.LinkedList;

import junit.framework.Assert;

import org.junit.Test;

import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.domain.Beer;
import ch.hsr.bieridee.domain.Beertype;
import ch.hsr.bieridee.domain.Brewery;
import ch.hsr.bieridee.domain.Tag;

public class ResTest {

	@Test
	public void beerUriTest() {
		Beer b = new Beer(3, "name", "brand", "pic", new LinkedList<Tag>(), new Beertype("", ""), new Brewery("", "", "", ""));
		String URI = Res.API_URL + Res.BEER_DOCUMENT.replaceAll("\\{.*\\}", "3");
		Assert.assertEquals(URI, Res.getResourceUri(b));
	}

}
