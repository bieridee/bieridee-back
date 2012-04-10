package ch.hsr.bieridee.config;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.module.SimpleModule;

import ch.hsr.bieridee.serializer.BeerListSerializer;
import ch.hsr.bieridee.serializer.BeerSerializer;

/**
 * Configuration constants.
 *
 */
public final class Config {
	
	private static ObjectMapper OBJECT_MAPPER;
	
	public static final String BEER_RESOURCE = "/beers";
	public static final String BEERTYPE_RESOURCE = "/beertypes";
	
	/**
	 * Relative path to the neo4j graph database.
	 */
	public static final String DB_PATH = "var/Brauhaus";
	
	// dummy constructor
	private Config() {
		// do not instanciate.
	}
	
	/**
	 * Returns the ObjectMapper for the JacksonSerializations.
	 * 
	 * @return ObjectMapper
	 */
	public static ObjectMapper getObjectMapper() {
		if(OBJECT_MAPPER != null) {
			return OBJECT_MAPPER;
		}
		OBJECT_MAPPER= new ObjectMapper();
		OBJECT_MAPPER.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
		final SimpleModule beerModule = new SimpleModule("BierIdeeModule", new Version(1, 0, 0, null));
		beerModule.addSerializer(new BeerSerializer());
		beerModule.addSerializer(new BeerListSerializer());
		OBJECT_MAPPER.registerModule(beerModule);
		return OBJECT_MAPPER;
	}
	
}
