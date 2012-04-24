package ch.hsr.bieridee.config;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.module.SimpleModule;

import ch.hsr.bieridee.serializer.BeerListSerializer;
import ch.hsr.bieridee.serializer.BeerSerializer;
import ch.hsr.bieridee.serializer.BeertypeListSerializer;
import ch.hsr.bieridee.serializer.BeertypeSerializer;
import ch.hsr.bieridee.serializer.BreweryListSerializer;
import ch.hsr.bieridee.serializer.BrewerySerializer;
import ch.hsr.bieridee.serializer.TagListSerializer;
import ch.hsr.bieridee.serializer.TagSerializer;
import ch.hsr.bieridee.serializer.UserListSerializer;
import ch.hsr.bieridee.serializer.UserSerializer;
import ch.hsr.bieridee.utils.ConfigManager;

/**
 * Configuration constants.
 * 
 */
public final class Config {

	private static ObjectMapper OBJECT_MAPPER;

	/**
	 * Relative path to the neo4j graph database.
	 */
	public static final String PROPERTY_DB_PATH = "Bieridee.api.dir";
	public static final String PROPERTY_API_PORT = "Bieridee.api.port";
	public static final String PROPERTY_API_HOST = "Bieridee.api.host";
	public static final String PROPERTY_DB_HOST = "Bieridee.db.host";
	public static final String PROPERTY_DB_ADMIN_PORT = "Bieridee.db.admin.port";

	public static final String DB_PATH = ConfigManager.getManager().getStringProperty(PROPERTY_DB_PATH, "var/Testdb");
	public static final String DB_HOST = ConfigManager.getManager().getStringProperty(PROPERTY_DB_HOST, "localhost");

	public static final String API_HOST = ConfigManager.getManager().getStringProperty(PROPERTY_API_HOST, "localhost");
	public static final int API_PORT = ConfigManager.getManager().getIntProperty(PROPERTY_API_PORT, 8080);

	public static final int NEO4J_WEBADMIN_PORT = ConfigManager.getManager().getIntProperty(PROPERTY_DB_ADMIN_PORT, 7474);

	// dummy constructor
	private Config() {
		// do not instantiate.
	}

	/**
	 * Returns the ObjectMapper for the JacksonSerializations.
	 * 
	 * @return ObjectMapper
	 */
	public static ObjectMapper getObjectMapper() {

		if (OBJECT_MAPPER != null) {
			return OBJECT_MAPPER;
		}

		synchronized (Config.class) {
			if (OBJECT_MAPPER == null) {
				OBJECT_MAPPER = new ObjectMapper();
				OBJECT_MAPPER.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);

				final SimpleModule beerModule = new SimpleModule("BierIdeeModule", new Version(1, 0, 0, null));
				beerModule.addSerializer(new BeerSerializer());
				beerModule.addSerializer(new BeerListSerializer());
				beerModule.addSerializer(new BeertypeSerializer());
				beerModule.addSerializer(new BeertypeListSerializer());
				beerModule.addSerializer(new BrewerySerializer());
				beerModule.addSerializer(new BreweryListSerializer());
				beerModule.addSerializer(new TagSerializer());
				beerModule.addSerializer(new TagListSerializer());
				beerModule.addSerializer(new UserSerializer());
				beerModule.addSerializer(new UserListSerializer());

				OBJECT_MAPPER.registerModule(beerModule);
			}
			return OBJECT_MAPPER;
		}
	}

}
