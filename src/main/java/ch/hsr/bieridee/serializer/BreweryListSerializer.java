package ch.hsr.bieridee.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import ch.hsr.bieridee.models.BreweryModel;

/**
 * JSON serializer for an array of brewery objects.
 */
public class BreweryListSerializer extends JsonSerializer<BreweryModel[]> {

	@Override
	public void serialize(BreweryModel[] breweryModelList, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

		jsonGenerator.writeStartArray();
		for (BreweryModel breweryModel : breweryModelList) {
			new BrewerySerializer().serialize(breweryModel, jsonGenerator, serializerProvider);
		}
		jsonGenerator.writeEndArray();
	}

	@Override
	public Class<BreweryModel[]> handledType() {
		return BreweryModel[].class;
	}

}