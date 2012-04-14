package ch.hsr.bieridee.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.domain.Beertype;

/**
 * Json Serializer for the beertype domain class.
 * 
 * @author jfurrer
 *
 */
public class BeertypeSerializer extends JsonSerializer<Beertype> {

	@Override
	public void serialize(Beertype beertype, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
		jsonGenerator.writeStartObject();
		jsonGenerator.writeStringField("name", beertype.getName());
		jsonGenerator.writeStringField("description", beertype.getDescription());
		jsonGenerator.writeStringField("uri", Res.getResourceUri(beertype));
		jsonGenerator.writeEndObject();
	}
	
	@Override
	public Class<Beertype> handledType() {
		return Beertype.class;
	}

}
