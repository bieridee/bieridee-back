package ch.hsr.bieridee.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.models.BreweryModel;

/**
 * Json Serializer for the brewery domain class.
 */
public class BrewerySerializer extends JsonSerializer<BreweryModel> {

	@Override
	public void serialize(BreweryModel brewery , JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
		
		jsonGenerator.writeStartObject();
		jsonGenerator.writeNumberField("id", brewery.getId());
		jsonGenerator.writeStringField("name", brewery.getName());
		jsonGenerator.writeStringField("size", brewery.getSize());
		jsonGenerator.writeStringField("description", brewery.getDescription());
		jsonGenerator.writeStringField("picture", brewery.getPicture());
		jsonGenerator.writeStringField("uri", Res.getResourceUri(brewery));
		jsonGenerator.writeEndObject();
		
	}

	@Override
	public Class<BreweryModel> handledType() {
		return BreweryModel.class;
	}

}
