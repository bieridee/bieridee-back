package ch.hsr.bieridee.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import ch.hsr.bieridee.models.BeertypeModel;

/**
 * Json Serializer for the beertype domain class.
 */
public class BeertypeListSerializer extends JsonSerializer<BeertypeModel[]> {

	@Override
	public void serialize(BeertypeModel[] beertypeModelList, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
		jsonGenerator.writeStartArray();
		for (BeertypeModel beertypeModel : beertypeModelList) {
			new BeertypeSerializer().serialize(beertypeModel, jsonGenerator, serializerProvider);
		}
		jsonGenerator.writeEndArray();
	}
	
	@Override
	public Class<BeertypeModel[]> handledType() {
		return BeertypeModel[].class;
	}

}
