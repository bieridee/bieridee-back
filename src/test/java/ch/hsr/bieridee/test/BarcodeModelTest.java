package ch.hsr.bieridee.test;

import ch.hsr.bieridee.exceptions.WrongNodeTypeException;
import ch.hsr.bieridee.models.BarcodeModel;
import ch.hsr.bieridee.models.BeerModel;
import ch.hsr.bieridee.models.TagModel;
import ch.hsr.bieridee.utils.DBUtil;
import org.junit.Assert;
import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;

/**
 * Tests the BarcodeModel Class.
 * 
 */
public class BarcodeModelTest extends DBTest {
	/**
	 * Creates new Barcode, links it with a BeerModel and
	 * tries to get the Beer for that Barcode.
	 */
	@Test
	public void testBarcodeBeerInteraction() {
		final String code = "76129049";
		final String format = "EAN-8";
		BeerModel beerModel = null;
		BarcodeModel barcodeModel = null;

		// Create barcode model, add it to beer model
		try {
			barcodeModel = BarcodeModel.create(code, format);
			beerModel = new BeerModel(29);
			beerModel.addBarcode(barcodeModel);
		} catch (WrongNodeTypeException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (RuntimeException e) {
			e.printStackTrace();
			Assert.fail();
		}

		// Validate barcode model
		Assert.assertEquals(code, barcodeModel.getCode());
		Assert.assertEquals(format, barcodeModel.getFormat());

		// Retrieve beer from DB by barcode, compare with original beer model
		try {
			final Node beerNode = DBUtil.getBeerByBarcode(code);
			Assert.assertEquals(beerModel, new BeerModel(beerNode));
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (WrongNodeTypeException e) {
			e.printStackTrace();
		}
	}
}
