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
		final String code = "13371337";
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


	/**
	 * Tests whether an error is thrown when creating two identical barcodes.
	 */
	@Test
	public void testBarcodeDuplicationPrevention() {
		final String code = "2342667";
		final String format = "EAN-8";

		// First instance
		try {
			final BarcodeModel barcodeModel1 = BarcodeModel.create(code, format);
			final BeerModel beerModel = new BeerModel(29);
			beerModel.addBarcode(barcodeModel1);
		} catch (RuntimeException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (WrongNodeTypeException e) {
			e.printStackTrace();
			Assert.fail();
		}

		// Second instance
		try {
			final BarcodeModel barcodeModel2 = BarcodeModel.create(code, format);
			final BeerModel beerModel = new BeerModel(30);
			beerModel.addBarcode(barcodeModel2);

			// Should never get here
			Assert.fail("No RuntimeError was thrown for duplicate barcodes.");
		} catch (WrongNodeTypeException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (RuntimeException e) {
			// Expected exception. Success.
		}
	}
}
