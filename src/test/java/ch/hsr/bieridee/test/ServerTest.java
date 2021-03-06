package ch.hsr.bieridee.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import ch.hsr.bieridee.Main;

/**
 * Base class for all tests needing access to the REST api.
 * 
 */
public abstract class ServerTest {

	/**
	 * shuts down the REST api server.
	 */
	@AfterClass
	public static void shutdownDB() {
		Main.stopAPI();
		Main.stopDB();
	}

	/**
	 * starts the REST api server.
	 */
	@BeforeClass
	public static void startResletAPI() {
		Main.startDB(true);
		Main.startAPI();
	}

}
