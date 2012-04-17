package ch.hsr.bieridee.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import ch.hsr.bieridee.config.Res;
import ch.hsr.bieridee.domain.Tag;

/**
 * JSON serializer for the tag class.
 */
public class TagSerializer extends JsonSerializer<Tag> {

	@Override
	public void serialize(Tag tag, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
		jsonGenerator.writeStartObject();
		jsonGenerator.writeStringField("name", tag.getName());
		jsonGenerator.writeStringField("uri", Res.getResourceUri(tag));
		jsonGenerator.writeEndObject();
	}
	
	@Override
	public Class<Tag> handledType() {
		return Tag.class;
	}

}
