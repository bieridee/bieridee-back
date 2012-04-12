package ch.hsr.bieridee.utils;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.server.WrappingNeoServerBootstrapper;
import org.neo4j.server.configuration.EmbeddedServerConfigurator;

import ch.hsr.bieridee.config.RelType;

public class Testdb {
	static WrappingNeoServerBootstrapper SRV;
	static final String WEISSBIER = " Das Aroma der obergärigen Hefe, die Brimse, prägt den Charakter der Weissbiere Die Resthefe im Flaschenboden trübt die Flüssigkeit und verleiht den Bieren meist einen leichten Bananen-Nelken Geschmack.";
	static final String SPEZIALBIER = "Im Klassiker Spezial verbindet der Brauer den milden, schlanken Malzkörper mit kekonnt dosiertem Hopfenbitter. Meist rezent auf der Zungenspitze regt es den Gaumen damit kräftig an. Ideal als Begleiter zu kräftigen Gerichten.";
	static final String EDELUNDLEICHT = "für weicheier";

	public static EmbeddedGraphDatabase createDB(String path) {
		deleteDir(path);
		final EmbeddedGraphDatabase GRAPHDB = new EmbeddedGraphDatabase(path);
		EmbeddedServerConfigurator config;
		config = new EmbeddedServerConfigurator(GRAPHDB);
		config.configuration().setProperty("org.neo4j.server.webserver.address", "0.0.0.0");
		SRV = new WrappingNeoServerBootstrapper(GRAPHDB, config);

		SRV.start();
		registerShutdownHook(GRAPHDB);
		return GRAPHDB;
	}

	private static void deleteDir(String path) {
		File file = new File(path);
		try {
			for (File f : file.listFiles()) {
				f.delete();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static Node createTag(EmbeddedGraphDatabase db, String name) {
		final Node tag = db.createNode();
		tag.setProperty("type", "tag");
		tag.setProperty("name", name);
		return tag;
	}

	private static Node createBeertype(EmbeddedGraphDatabase db, String name, String description) {
		final Node beertype = db.createNode();
		beertype.setProperty("type", "beertype");
		beertype.setProperty("name", name);
		beertype.setProperty("description", description);
		return beertype;
	}

	private static Node createBeerNode(EmbeddedGraphDatabase db, String brand, String name, String image) {
		final Node beer = db.createNode();
		beer.setProperty("type", "beer");
		beer.setProperty("brand", brand);
		beer.setProperty("name", name);
		beer.setProperty("image", image);
		return beer;
	}

	private static void addAction(EmbeddedGraphDatabase db, Node newAction) {
		Node home = db.getReferenceNode();
		Node timeLineStart = home.getSingleRelationship(RelType.INDEX_TIMELINESTART, Direction.OUTGOING).getEndNode();
		Relationship relationToNext = timeLineStart.getSingleRelationship(RelType.NEXT, Direction.OUTGOING);
		Node next = relationToNext.getEndNode();
		relationToNext.delete();
		timeLineStart.createRelationshipTo(newAction, RelType.NEXT);
		newAction.createRelationshipTo(next, RelType.NEXT);
	}

	private static Node createUser(EmbeddedGraphDatabase db, String prename, String surname, String email, String username, String password) {
		final Node user = db.createNode();
		user.setProperty("type", "user");
		user.setProperty("prename", prename);
		user.setProperty("surname", surname);
		user.setProperty("email", email);
		user.setProperty("username", username);
		user.setProperty("password", password);
		return user;
	}

	public static EmbeddedGraphDatabase fillDB(EmbeddedGraphDatabase db) {
		Transaction transaction = db.beginTx();

		try {
			final Node rootNode = db.getReferenceNode();

			final Node userIndex = db.createNode();
			final Node beerIndex = db.createNode();
			final Node beertypeIndex = db.createNode();
			final Node tagIndex = db.createNode();
			final Node breweryIndex = db.createNode();
			final Node timelineIndex = db.createNode();

			final Node timelineStart = db.createNode();
			final Node timeLineEnde = db.createNode();
			timelineStart.createRelationshipTo(timeLineEnde, RelType.NEXT);

			rootNode.createRelationshipTo(userIndex, RelType.INDEX_USER);
			rootNode.createRelationshipTo(beerIndex, RelType.INDEX_BEER);
			rootNode.createRelationshipTo(tagIndex, RelType.INDEX_TAG);
			rootNode.createRelationshipTo(breweryIndex, RelType.INDEX_BREWERY);
			rootNode.createRelationshipTo(timelineIndex, RelType.INDEX_TIMELINE);
			rootNode.createRelationshipTo(beertypeIndex, RelType.INDEX_BEERTYPE);
			rootNode.createRelationshipTo(timelineStart, RelType.INDEX_TIMELINESTART);

			/* CREATE BEERS */

			final Node feldschloesschen = createBeerNode(db, "Feldschlösschen", "Feldschlösschen Ice-Beer", "");
			final Node falken = createBeerNode(db, "Falken", "Falken First Cool", "");
			final Node calanda = createBeerNode(db, "Calanda", "Calanda Meisterbräu", "");
			final Node waedibraeu = createBeerNode(db, "Wädibräu", "Ur-Weizen Wädibräu", "");

			beerIndex.createRelationshipTo(feldschloesschen, RelType.INDEXES);
			beerIndex.createRelationshipTo(falken, RelType.INDEXES);
			beerIndex.createRelationshipTo(calanda, RelType.INDEXES);
			beerIndex.createRelationshipTo(waedibraeu, RelType.INDEXES);

			/* CREATE BEERTYPES */

			final Node weissbier = createBeertype(db, "Weissbier", WEISSBIER);
			final Node spezial = createBeertype(db, "Blond", SPEZIALBIER);
			final Node edelundleicht = createBeertype(db, "Edel und Leicht", EDELUNDLEICHT);

			beertypeIndex.createRelationshipTo(weissbier, RelType.INDEXES);
			beertypeIndex.createRelationshipTo(spezial, RelType.INDEXES);
			beertypeIndex.createRelationshipTo(edelundleicht, RelType.INDEXES);

			/* CREATE BEER TO BEERTYPE RELATIONS */

			feldschloesschen.createRelationshipTo(edelundleicht, RelType.HAS_BEERTYPE);
			falken.createRelationshipTo(edelundleicht, RelType.HAS_BEERTYPE);
			calanda.createRelationshipTo(spezial, RelType.HAS_BEERTYPE);
			waedibraeu.createRelationshipTo(weissbier, RelType.HAS_BEERTYPE);

			/* CREATE TAGS */

			final Node lecker = createTag(db, "lecker");
			final Node wuerzig = createTag(db, "würzig");
			final Node suess = createTag(db, "süss");
			final Node leicht = createTag(db, "leicht");
			final Node billig = createTag(db, "billig");
			final Node gruusig = createTag(db, "gruusig");

			tagIndex.createRelationshipTo(lecker, RelType.INDEXES);
			tagIndex.createRelationshipTo(wuerzig, RelType.INDEXES);
			tagIndex.createRelationshipTo(suess, RelType.INDEXES);
			tagIndex.createRelationshipTo(leicht, RelType.INDEXES);
			tagIndex.createRelationshipTo(billig, RelType.INDEXES);
			tagIndex.createRelationshipTo(gruusig, RelType.INDEXES);

			/* ADD TAGS TO BEERS */

			feldschloesschen.createRelationshipTo(gruusig, RelType.HAS_TAG);
			falken.createRelationshipTo(billig, RelType.HAS_TAG);
			falken.createRelationshipTo(leicht, RelType.HAS_TAG);
			waedibraeu.createRelationshipTo(wuerzig, RelType.HAS_TAG);
			calanda.createRelationshipTo(lecker, RelType.HAS_TAG);
			calanda.createRelationshipTo(suess, RelType.HAS_TAG);

			/* CREATE USERS */

			final Node danilo = createUser(db, "danilo", "bargen", "bargi@beer.ch", "säufer", getSHA1("******"));
			final Node jonas = createUser(db, "jonas", "furrer", "jonas@beer.ch", "alki", getSHA1("ILIKECOFFEE"));
			final Node chrigi = createUser(db, "chrigi", "fässler", "chrigi@beer.ch", "trinker", getSHA1("DjBobo"));
			final Node urs = createUser(db, "urs", "baumann", "urs@beer.ch", "üse", getSHA1("creat user : user with password"));

			userIndex.createRelationshipTo(danilo, RelType.INDEXES);
			userIndex.createRelationshipTo(jonas, RelType.INDEXES);
			userIndex.createRelationshipTo(chrigi, RelType.INDEXES);
			userIndex.createRelationshipTo(urs, RelType.INDEXES);

			/* CREATE RATINGS */
			final Node rating1 = createRating(db, 1);
			final Node rating2 = createRating(db, 2);
			final Node rating3 = createRating(db, 3);
			final Node rating4 = createRating(db, 5);
			final Node rating5 = createRating(db, 4);
			final Node rating6 = createRating(db, 3);

			timelineIndex.createRelationshipTo(rating1, RelType.INDEXES);
			timelineIndex.createRelationshipTo(rating2, RelType.INDEXES);
			timelineIndex.createRelationshipTo(rating3, RelType.INDEXES);
			timelineIndex.createRelationshipTo(rating4, RelType.INDEXES);
			timelineIndex.createRelationshipTo(rating5, RelType.INDEXES);
			timelineIndex.createRelationshipTo(rating6, RelType.INDEXES);

			jonas.createRelationshipTo(rating1, RelType.DOES);
			rating1.createRelationshipTo(feldschloesschen, RelType.CONTAINS);

			jonas.createRelationshipTo(rating2, RelType.DOES);
			rating1.createRelationshipTo(falken, RelType.CONTAINS);

			jonas.createRelationshipTo(rating3, RelType.DOES);
			rating1.createRelationshipTo(calanda, RelType.CONTAINS);

			chrigi.createRelationshipTo(rating4, RelType.DOES);
			rating1.createRelationshipTo(calanda, RelType.CONTAINS);

			chrigi.createRelationshipTo(rating4, RelType.DOES);
			rating1.createRelationshipTo(waedibraeu, RelType.CONTAINS);

			danilo.createRelationshipTo(rating5, RelType.DOES);
			rating1.createRelationshipTo(feldschloesschen, RelType.CONTAINS);

			urs.createRelationshipTo(rating6, RelType.DOES);
			rating1.createRelationshipTo(feldschloesschen, RelType.CONTAINS);

			/* CREATE CONSUMPTIONS */
			final Node c1 = createConsumption(db);
			final Node c2 = createConsumption(db);
			final Node c3 = createConsumption(db);
			final Node c4 = createConsumption(db);
			final Node c5 = createConsumption(db);

			c1.createRelationshipTo(feldschloesschen, RelType.CONTAINS);
			jonas.createRelationshipTo(c1, RelType.DOES);

			c2.createRelationshipTo(feldschloesschen, RelType.CONTAINS);
			chrigi.createRelationshipTo(c2, RelType.DOES);

			c3.createRelationshipTo(calanda, RelType.CONTAINS);
			urs.createRelationshipTo(c3, RelType.DOES);

			c4.createRelationshipTo(falken, RelType.CONTAINS);
			urs.createRelationshipTo(c4, RelType.DOES);

			c5.createRelationshipTo(waedibraeu, RelType.CONTAINS);
			danilo.createRelationshipTo(c5, RelType.DOES);

			/* INSERT CONSUMPTION TO TIMELINE */

			addAction(db, rating1);
			addAction(db, c1);
			addAction(db, c2);
			addAction(db, rating2);
			addAction(db, c3);
			addAction(db, rating3);
			addAction(db, rating4);
			addAction(db, c4);
			addAction(db, c5);
			addAction(db, rating5);
			addAction(db, rating6);

			transaction.success();
		} finally {
			transaction.finish();
		}

		return db;
	}

	private static String getSHA1(String pw) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(md.digest(pw.getBytes()));
	}

	private static Node createConsumption(EmbeddedGraphDatabase db) {
		final Node consumption = db.createNode();
		consumption.setProperty("type", "consumption");
		consumption.setProperty("timestamp", System.currentTimeMillis());
		return consumption;
	}

	private static Node createRating(EmbeddedGraphDatabase db, int value) {
		final Node rating = db.createNode();
		rating.setProperty("type", "rating");
		rating.setProperty("timestamp", System.currentTimeMillis());
		rating.setProperty("rating", value);
		return rating;
	}

	private static void registerShutdownHook(final GraphDatabaseService graphDb) {
		// Registers a shutdownsrv hook for the Neo4j instance so that it
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

}
