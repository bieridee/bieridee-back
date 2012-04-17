package ch.hsr.bieridee.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import ch.hsr.bieridee.domain.Brewery;

/**
 * JSON serializer for an array of brewery objects.
 */
public class BreweryListSerializer extends JsonSerializer<Brewery[]> {

	@Override
	public void serialize(Brewery[] breweryList, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

		jsonGenerator.writeStartArray();
		for (Brewery brewery : breweryList) {
			new BrewerySerializer().serialize(brewery, jsonGenerator, serializerProvider);
		}
		jsonGenerator.writeEndArray();
	}

	@Override
	public Class<Brewery[]> handledType() {
		return Brewery[].class;
	}

}
