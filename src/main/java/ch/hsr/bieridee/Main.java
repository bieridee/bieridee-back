package ch.hsr.bieridee;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.server.WrappingNeoServerBootstrapper;
import org.neo4j.server.configuration.EmbeddedServerConfigurator;
import org.restlet.Component;
import org.restlet.data.Protocol;

import ch.hsr.bieridee.config.Config;
import ch.hsr.bieridee.utils.Testdb;

/**
 * Main Class to start the application.
 * 
 */
public final class Main {
	private static EmbeddedGraphDatabase GRAPHDB;
	private static WrappingNeoServerBootstrapper SRV;
	private static final int SERVER_PORT = 8080;

	private Main() {
		// do not instantiate.
	}

	/**
	 * The main!
	 * 
	 * @param args
	 *            ARGH
	 */
	public static void main(String[] args) {
		// Create new database
		// TESTING ONLY: deletes and rebuilds db every time
		//GRAPHDB = Testdb.createDB(Config.DB_PATH);

		// ////////////////////////////////////////

		// Create a new Restlet Component.
		final Component component = new Component();

		// Add a new HTTP server listening on a local port
		component.getServers().add(Protocol.HTTP, SERVER_PORT);

		// Create the graph database
		GRAPHDB = Main.getGraphDb();

		// Attach the dispatcher.
		component.getDefaultHost().attach(new Dispatcher());

		// Start the component.
		try {
			component.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void registerShutdownHook(final GraphDatabaseService graphDb) {
		// Registers a shutdosrvwn hook for the Neo4j instance so that it
		// shuts down nicely when the VM exits (even if you "Ctrl-C" the
		// running example before it's completed)
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				graphDb.shutdown();
				SRV.stop();
			}
		});
	}

	/**
	 * Gets the current instance of the running graphdb or starts the instance.
	 * 
	 * @return The GraphDB
	 */
	public static EmbeddedGraphDatabase getGraphDb() {
		if (GRAPHDB == null) {
			// TODO: Remove for production use.
			GRAPHDB = Testdb.createDB(Config.DB_PATH);
			Testdb.fillDB(GRAPHDB);
			//GRAPHDB = new EmbeddedGraphDatabase(Config.DB_PATH);
			
			EmbeddedServerConfigurator config;
			config = new EmbeddedServerConfigurator(Main.GRAPHDB);
			config.configuration().setProperty("org.neo4j.server.webserver.address", "0.0.0.0");

			SRV = new WrappingNeoServerBootstrapper(GRAPHDB, config);
			SRV.start();
			registerShutdownHook(GRAPHDB);

		}
		return GRAPHDB;
	}
}
