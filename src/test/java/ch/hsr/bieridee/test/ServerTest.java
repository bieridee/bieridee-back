package ch.hsr.bieridee.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.restlet.Component;
import org.restlet.data.Protocol;

import ch.hsr.bieridee.Dispatcher;

/**
 * Base class for all tests needing access to the REST api.
 * 
 */
public abstract class ServerTest {

	final static int SERVER_PORT = 8080;
	public final static String BASE_URL = "http://localhost:" + SERVER_PORT;
	private static Component COMPONENT;

	/**
	 * shuts down the REST api server.
	 */
	@AfterClass
	public static void shutdownDB() {
		if (!COMPONENT.isStopped()) {
			System.out.println("Stopping Restlet API");
			try {
				COMPONENT.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * starts the REST api server.
	 */
	@BeforeClass
	public static void startResletAPI() {
		if (COMPONENT == null) {
			COMPONENT = new Component();

			// Add a new HTTP server listening on a local port
			COMPONENT.getServers().add(Protocol.HTTP, SERVER_PORT);

			// Create the graph database
			// GRAPHDB = Main.getGraphDb();

			// Attach the dispatcher.
			COMPONENT.getDefaultHost().attach(new Dispatcher());
		}
		if (!COMPONENT.isStarted()) {
			System.out.println("Starting Restlet API");
			// System.out.println("starting reslet and db");
			// GRAPHDB = Testdb.createDB(Config.DB_PATH);
			// Testdb.fillDB(GRAPHDB);
			COMPONENT = new Component();

			// Add a new HTTP server listening on a local port
			COMPONENT.getServers().add(Protocol.HTTP, SERVER_PORT);

			// Attach the dispatcher.
			COMPONENT.getDefaultHost().attach(new Dispatcher());

			try {
				COMPONENT.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
