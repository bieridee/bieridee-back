package ch.hsr.bieridee;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class Neo4jTestResource extends ServerResource {
	
	private static enum RelTypes implements RelationshipType { KNOWS }
	private GraphDatabaseService graphDb;
	private Node beer1, beer2, user1;
	private Relationship relationship1, relationship2;

	private void init() {
		graphDb = Main.getGraphDb();
	}
	
	private void createData() {
		Transaction tx = graphDb.beginTx();
		try
		{
			beer1 = graphDb.createNode();
			beer1.setProperty("type", "beer");
			beer1.setProperty("name", "Kilkenny");
			
			beer2 = graphDb.createNode();
			beer2.setProperty("type", "beer");
			beer2.setProperty("name", "Quöllfrisch");
			
			user1 = graphDb.createNode();
			user1.setProperty("type", "user");
			user1.setProperty("name", "Danilo");
			
			relationship1 = user1.createRelationshipTo(beer1, RelTypes.KNOWS);
			relationship1.setProperty("comment", "Awesome!");
			relationship2 = user1.createRelationshipTo(beer2, RelTypes.KNOWS);
			relationship2.setProperty("comment", "Ganz OK! :)");
			
		    tx.success();
		}
		finally
		{
		    tx.finish();
		}
	}
	
	private String fetchData() {
		Transaction tx = graphDb.beginTx();
		String out = "";
		try
		{
			String name = (String)user1.getProperty("name");
			for (Relationship rel : user1.getRelationships()) {
				Node end = rel.getEndNode();
				out += name + " knows " + end.getProperty("name") + ".";
				out += " He thinks it's \"" + rel.getProperty("comment") + "\".\r\n";
			}
			
			beer1 = graphDb.createNode();
			beer1.setProperty("type", "beer");
			beer1.setProperty("name", "Kilkenny");
			
			beer2 = graphDb.createNode();
			beer2.setProperty("type", "beer");
			beer2.setProperty("name", "Quöllfrisch");
			
			user1 = graphDb.createNode();
			user1.setProperty("type", "user");
			user1.setProperty("name", "Danilo");
			
			relationship1 = user1.createRelationshipTo(beer1, RelTypes.KNOWS);
			relationship1.setProperty("comment", "Awesome!");
			relationship2 = user1.createRelationshipTo(beer2, RelTypes.KNOWS);
			relationship2.setProperty("comment", "Ganz OK...");
			
		    tx.success();
		}
		finally
		{
		    tx.finish();
		}
		return out;
	}
	
	@Get  
    public String represent() {   
		init();
		createData();
		return fetchData();
    }
	
}
