package ch.hsr.bieridee.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import ch.hsr.bieridee.Main;

/**
 * Base class for database tests. Ensures that the database is available and cleaned after each test.
 * 
 */
public abstract class DBTest {
	/**
	 * shuts down the REST api server.
	 */
	@AfterClass
	public static void shutdownDB() {
		Main.stopDB();
	}

	/**
	 * starts the REST api server.
	 */
	@BeforeClass
	public static void startResletAPI() {
		Main.startDB(true);
	}

}
