package ch.hsr.bieridee.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import ch.hsr.bieridee.domain.Tag;

/**
 * JSON serializer for an array of tags.
 *
 */
public class TagListSerializer extends JsonSerializer<Tag[]> {

	@Override
	public void serialize(Tag[] tags, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
		jsonGenerator.writeStartArray();
		for(Tag tag : tags) {
			new TagSerializer().serialize(tag, jsonGenerator, serializerProvider);
		}
		jsonGenerator.writeEndArray();
	}
	
	@Override
	public Class<Tag[]> handledType() {
		return Tag[].class;
	}

}
