package ch.hsr.bieridee.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import ch.hsr.bieridee.domain.Beertype;

/**
 * Json Serializer for the beertype domain class.
 * 
 * @author jfurrer
 *
 */
public class BeertypeListSerializer extends JsonSerializer<Beertype[]> {

	@Override
	public void serialize(Beertype[] beertypeList, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
		jsonGenerator.writeStartArray();
		for (Beertype beertype : beertypeList) {
			new BeertypeSerializer().serialize(beertype, jsonGenerator, serializerProvider);
		}
		jsonGenerator.writeEndArray();
	}
	
	@Override
	public Class<Beertype[]> handledType() {
		return Beertype[].class;
	}

}
