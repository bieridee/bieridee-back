package ch.hsr.bieridee;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.server.WrappingNeoServerBootstrapper;
import org.restlet.Component;
import org.restlet.data.Protocol;

/**
 * Main Class to start the application.
 *
 */
public final class Main {
	private static EmbeddedGraphDatabase graphDb;
	private static WrappingNeoServerBootstrapper srv;
	private static final int SERVER_PORT = 8181;
	
	private Main() {
		// do not instanciate.
	}
	
	/**
	 * The main!
	 * @param args ARGH
	 */
	public static void main(String[] args) {
			// Create a new Restlet Component.
			final Component component = new Component();

			// Add a new HTTP server listening on a local port
			component.getServers().add(Protocol.HTTP, SERVER_PORT);

			// Create the graph database
			graphDb = new EmbeddedGraphDatabase(ch.hsr.bieridee.config.Config.DB_PATH);
			srv = new WrappingNeoServerBootstrapper(graphDb);
			srv.start();
			registerShutdownHook(graphDb);

			// Attach the dispatcher.
			component.getDefaultHost().attach(new Dispatcher());

			// Start the component.
			try {
				component.start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	private static void registerShutdownHook(final GraphDatabaseService graphDb) {
		// Registers a shutdown hook for the Neo4j instance so that it
		// shuts down nicely when the VM exits (even if you "Ctrl-C" the
		// running example before it's completed)
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				graphDb.shutdown();
				srv.stop();
			}
		});
	}

	public static EmbeddedGraphDatabase getGraphDb() {
		if(graphDb == null) {
			graphDb = new EmbeddedGraphDatabase(ch.hsr.bieridee.config.Config.DB_PATH);
		}
		return graphDb;
	}
}
