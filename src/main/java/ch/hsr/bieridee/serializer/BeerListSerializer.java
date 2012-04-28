package ch.hsr.bieridee.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import ch.hsr.bieridee.models.BeerModel;

/**
 * JSON serializer for an array of beer objects.
 */
public class BeerListSerializer extends JsonSerializer<BeerModel[]> {

	@Override
	public void serialize(BeerModel[] beerList, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

		jsonGenerator.writeStartArray();
		for (BeerModel beerModel : beerList) {
			new BeerSerializer().serialize(beerModel, jsonGenerator, serializerProvider);
		}
		jsonGenerator.writeEndArray();
	}

	@Override
	public Class<BeerModel[]> handledType() {
		return BeerModel[].class;
	}

}
