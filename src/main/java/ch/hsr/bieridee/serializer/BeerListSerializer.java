package ch.hsr.bieridee.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import ch.hsr.bieridee.domain.Beer;

/**
 * JSON serializer for an array of beer objects.
 * 
 * @author jfurrer
 * 
 */
public class BeerListSerializer extends JsonSerializer<Beer[]> {

	@Override
	public void serialize(Beer[] beerList, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

		jsonGenerator.writeStartArray();
		for (Beer beer : beerList) {
			new BeerSerializer().serialize(beer, jsonGenerator, serializerProvider);
		}
		jsonGenerator.writeEndArray();
	}

	@Override
	public Class<Beer[]> handledType() {
		return Beer[].class;
	}

}
