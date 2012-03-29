package ch.hsr.bieridee;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.server.WrappingNeoServerBootstrapper;
import org.restlet.Component;
import org.restlet.data.Protocol;

public class Main {
	private static EmbeddedGraphDatabase graphDb;
	private static WrappingNeoServerBootstrapper srv;

	public static void main(String[] args) {
		try {
			// Create a new Restlet Component.
			Component component = new Component();

			// Add a new HTTP server listening on a local port
			component.getServers().add(Protocol.HTTP, 8181);

			// Create the graph database
			graphDb = new EmbeddedGraphDatabase(ch.hsr.bieridee.Config.DB_PATH);
			srv = new WrappingNeoServerBootstrapper(graphDb);
			srv.start();
			registerShutdownHook(graphDb);

			// Attach the dispatcher.
			component.getDefaultHost().attach(new Dispatcher());

			// Start the component.
			component.start();
		} catch (Exception e) {
			// Something is wrong.
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
		return graphDb;
	}
}
